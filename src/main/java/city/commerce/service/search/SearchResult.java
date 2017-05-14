package city.commerce.service.search;

import lombok.Data;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-26
 */
@Data
public class SearchResult {
    @Data
    public static class IndexStore {
        private int storeId;
        private double distance;
        private int[] productIds;
    }

    private List<IndexStore> indexStores;
    private int total;
}
