package city.commerce.service.search;

import city.commerce.entity.*;
import city.commerce.model.Store;
import city.commerce.repository.mapper.ProductMapper;
import city.commerce.repository.mapper.StoreMapper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.search.join.*;
import org.apache.lucene.spatial.SpatialStrategy;
import org.apache.lucene.spatial.prefix.RecursivePrefixTreeStrategy;
import org.apache.lucene.spatial.prefix.tree.GeohashPrefixTree;
import org.apache.lucene.spatial.prefix.tree.SpatialPrefixTree;
import org.apache.lucene.spatial.query.SpatialArgs;
import org.apache.lucene.spatial.query.SpatialOperation;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.Shape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-26
 */
@Service
public class SearchService {
    private static final String LOCATION_INDEX_KEY = "location";
    private static final int GEOHASH_LEVEL = 11;

    private Directory ramDirectory;
    private Directory dfDirectory;

    private SpatialContext spatialContext;
    private SpatialStrategy spatialStrategy;

    private IndexSearcher searcher;
    private IndexReader reader;
    private IndexWriter writer;
    private Analyzer analyzer;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StoreMapper storeMapper;

    private FacetsConfig facetConfig = new FacetsConfig();

    //@PostConstruct
    public void init() throws IOException {
        spatialContext = SpatialContext.GEO;
        SpatialPrefixTree spatialPrefixTree = new GeohashPrefixTree(spatialContext, GEOHASH_LEVEL);
        spatialStrategy = new RecursivePrefixTreeStrategy(spatialPrefixTree, LOCATION_INDEX_KEY);

        ramDirectory = new RAMDirectory();

        analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        index(ramDirectory, config);

        reader = DirectoryReader.open(ramDirectory);
        searcher = new IndexSearcher(reader);
    }

    //@PreDestroy
    public void destroy() {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SearchResult search(SearchCondition condition) {
        try {
            return search(this.searcher, condition);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SearchResult search(IndexSearcher searcher, SearchCondition condition) throws Exception {
        Sort sort = null;
        Location location = condition.getLocation();
        Point point = null;
        if (null != location) {
            point = spatialContext.getShapeFactory().pointXY(location.getLongitude(), location.getLatitude());
            ValueSource valueSource = spatialStrategy.makeDistanceValueSource(point, DistanceUtils.DEG_TO_KM);
            sort = new Sort(valueSource.getSortField(false)).rewrite(searcher);
        }


//        Query storeDistanceQuery = null;
//        double distance = condition.getDistance();
//        if (null != location && distance > 0) {
//            SpatialArgs args = new SpatialArgs(
//                    SpatialOperation.Intersects,
//                    spatialContext.getShapeFactory().circle(
//                            location.getLongitude(),
//                            location.getLatitude(),
//                            DistanceUtils.dist2Degrees(distance, DistanceUtils.EARTH_MEAN_RADIUS_KM)
//                    )
//            );
//            storeDistanceQuery = spatialStrategy.makeQuery(args);
//        }

        String keyword = condition.getKeyword();
        Query productQuery = null;
        if (null != keyword) {
            productQuery = new QueryParser("name", analyzer).parse(keyword);
        }

        int total = 0;
        List<SearchResult.IndexStore> indexStores = new ArrayList<>();

        BitSetProducer bitSetProducer = new QueryBitSetProducer(new TermQuery(new Term("type", "store")));
        ToParentBlockJoinQuery query = new ToParentBlockJoinQuery(productQuery, bitSetProducer, ScoreMode.None);
        ToParentBlockJoinCollector collector = new ToParentBlockJoinCollector(
                sort == null ? Sort.INDEXORDER : sort,//new Sort(new SortField("id", SortField.Type.INT)),
                10, false, false);
        searcher.search(query, collector);
        TopGroups groups = collector.getTopGroups(
                query,
                Sort.INDEXORDER,
                0,   // parent doc offset
                100,  // maxDocsPerGroup
                0,   // withinGroupOffset
                true // fillSortFields
        );

        if (null != groups && null != groups.groups) {
            total = groups.totalHitCount;
            for (GroupDocs docs : groups.groups) {
                SearchResult.IndexStore indexStore = new SearchResult.IndexStore();
                indexStores.add(indexStore);

                int[] productIds = new int[docs.scoreDocs.length];
                indexStore.setProductIds(productIds);

                int storeDocId = (Integer)docs.groupValue;
                Document storeDoc = searcher.doc(storeDocId);
                String storeLocation = storeDoc.getField(spatialStrategy.getFieldName()).stringValue();
                String[] locations = storeLocation.split(",");
                double xPoint = Double.parseDouble(locations[0]);
                double yPoint = Double.parseDouble(locations[1]);
                double distDEG = spatialContext.calcDistance(point, xPoint, yPoint);
                double storeDistance = DistanceUtils.degrees2Dist(distDEG, DistanceUtils.EARTH_MEAN_RADIUS_KM);
                indexStore.setDistance(storeDistance);
                indexStore.setStoreId(Integer.valueOf(storeDoc.getField("storeId").stringValue()));

                int i=0;
                for (ScoreDoc scoreDoc : docs.scoreDocs) {
                    int productDocId = scoreDoc.doc;
                    Document productDoc = searcher.doc(productDocId);
                    productIds[i++] = Integer.valueOf(productDoc.getField("productId").stringValue());
                }
            }
        }

        SearchResult result = new SearchResult();
        result.setTotal(total);
        result.setIndexStores(indexStores);
        return result;
    }

    public void addIndex(StoreEntity storeEntity, List<DivisionEntity> divisionEntities) throws IOException {
        index(ramDirectory, writer, storeEntity, divisionEntities);
    }

    public void updateIndex(StoreEntity store) throws IOException {
        writer.deleteDocuments(new Term("storeId", String.valueOf(store.getId())));
        index(ramDirectory, writer, store, null);
    }

    private void index(Directory directory, IndexWriterConfig config) throws IOException {
        System.out.println("index start...");
        writer = new IndexWriter(directory, config);
        storeMapper.all().forEach(storeEntity -> {
            try {
                index(directory, writer, storeEntity, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.commit();

        System.out.println("index end...");
    }

    private void index(Directory directory, IndexWriter writer, StoreEntity storeEntity, List<DivisionEntity> divisionEntities) throws IOException {
        DirectoryTaxonomyWriter taxoWriter = new DirectoryTaxonomyWriter(directory);

        List<Document> documents = new ArrayList<Document>();
        productMapper.findByStoreId(storeEntity.getId()).forEach(product -> {
            Document document = createDocument(product);
            documents.add(document);
        });

        Document document = createDocument(storeEntity, divisionEntities, spatialContext, spatialStrategy);
        documents.add(facetConfig.build(taxoWriter, document));

        writer.addDocuments(documents);
    }

    private Document createDocument(StoreEntity storeEntity, List<DivisionEntity> divisionEntities, SpatialContext ctx, SpatialStrategy strategy) {
        System.out.println("index Store: " + storeEntity.getId() + ", " + storeEntity.getName());

        Document document = new Document();
        document.add(new StringField("storeId", String.valueOf(storeEntity.getId()), Field.Store.YES));
        document.add(new StringField("type", "store", Field.Store.YES));
        document.add(new FacetField("division", divisionEntities.stream().toArray(String[]::new)));

        Shape shape = ctx.getShapeFactory().pointXY(Double.parseDouble(storeEntity.getLongitude()), Double.parseDouble(storeEntity.getLatitude()));
        Field[]fields = strategy.createIndexableFields(shape);
        for (Field field : fields) {
            document.add(field);
        }
        Point pt = (Point) shape;
        document.add(new StoredField(strategy.getFieldName(), pt.getX() + ","+ pt.getY()));
        return document;
    }

    private Document createDocument(ProductEntity product) {
        System.out.println("index Product: " + product.getId() + ", " + product.getName());

        Document document = new Document();
        document.add(new StoredField("productId", product.getId()));
        document.add(new StringField("storeId", String.valueOf(product.getStoreId()), Field.Store.YES));
        document.add(new TextField("name", product.getName(), Field.Store.NO));
        document.add(new StringField("type", "product", Field.Store.YES));
        return document;
    }
}
