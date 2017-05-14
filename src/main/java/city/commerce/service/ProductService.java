package city.commerce.service;

import city.commerce.entity.*;
import city.commerce.model.Attribute;
import city.commerce.model.Category;
import city.commerce.model.Product;
import city.commerce.model.ProductAttribute;
import city.commerce.model.api.StatusCode;
import city.commerce.model.error.ErrorCode;
import city.commerce.model.error.ServiceException;
import city.commerce.model.params.ProductAttributeValueParam;
import city.commerce.model.params.ProductParam;
import city.commerce.repository.ProductRepository;
import city.commerce.validation.Validation;
import city.commerce.validation.ValidationException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-16
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private StoreService storeService;

    public ProductEntity queryProduct(int productId) {
        ProductEntity productEntity = productRepository.queryProduct(productId);
        if (null == productEntity) {
            throw new ServiceException(StatusCode.ResourceNotFound);
        }

        return productEntity;
    }

    public ProductEntity save(ProductParam productParam) {
        Validation.validate(productParam);

        if (categoryService.getCategory(productParam.getCategoryId()) == null) {
            throw new ValidationException("categoryId", ErrorCode.FIELD_INVALID);
        }

        if (storeService.getStore(productParam.getStoreId()) == null) {
            throw new ValidationException("storeId", ErrorCode.FIELD_INVALID);
        }

        if (productParam.getId() > 0) {
            return productRepository.updateProduct(convert(productParam));
        } else {
            return productRepository.addProduct(convert(productParam));
        }
    }

    public ProductEntity convert(ProductParam productParam) {
        CategoryEntity categoryEntity = categoryService.getCategory(productParam.getCategoryId());
        if (null == categoryEntity) {
            throw new ValidationException("categoryId", ErrorCode.FIELD_INVALID);
        }

        StoreEntity storeEntity = storeService.getStore(productParam.getStoreId());
        if (null == storeEntity) {
            throw new ValidationException("storeId", ErrorCode.FIELD_INVALID);
        }


        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productParam.getId());
        productEntity.setCode(productParam.getCode());
        productEntity.setName(productParam.getName());
        productEntity.setDescription(productParam.getDescription());
        productEntity.setInventory(productParam.getInventory());
        productEntity.setPrice(productParam.getPrice());
        productEntity.setCurrency(productParam.getCurrency().getCode());
        productEntity.setCategoryId(categoryEntity.getId());
        productEntity.setStoreId(storeEntity.getId());
        productEntity.setVendorId(storeEntity.getVendorId());

        productEntity.setModified((int) (System.currentTimeMillis()/1000));
        if (productEntity.getId() <= 0) {
            productEntity.setCreated(productEntity.getModified());
        }

        productEntity.setStatus(productParam.getStatus().getStatus());

        return productEntity;
    }

    public List<ProductAttribute> queryCheckedProductAttributes(int productId) {
        List<ProductAttribute> productAttributes = new ArrayList<>();

        Map<Integer, Map<Integer, ProductAttributeValueEntity>> productCheckedAttributeValueMap = queryProductAttributeValues(productId);
        for (Map.Entry<Integer, Map<Integer, ProductAttributeValueEntity>> entry : productCheckedAttributeValueMap.entrySet()) {
            ProductAttribute productAttribute = new ProductAttribute();
            int attributeId = entry.getKey();
            Attribute attribute = attributeService.queryAttribute(attributeId);
            if (null != attribute) {
                productAttribute.setAttributeId(attributeId);
                productAttribute.setName(attribute.getName());
                productAttribute.setDescription(attribute.getDescription());
                Map<Integer, ProductAttributeValueEntity> productCheckedValueMap = productCheckedAttributeValueMap.get(attributeId);
                if (CollectionUtils.isNotEmpty(attribute.getValues())) {
                    for (Attribute.Value value : attribute.getValues()) {
                        ProductAttributeValueEntity entity = productCheckedValueMap.get(value.getId());
                        if (null != entity) {
                            productAttribute.addValue(new ProductAttribute.Value(entity, value.getValue()));
                        }
                    }
                } else {
                    productAttribute.setValues(convert(productCheckedValueMap.values()));
                }
            } else {
                productAttribute.setValues(convert(entry.getValue().values()));
            }
        }

        return productAttributes;
    }

    private Map<Integer, Map<Integer, ProductAttributeValueEntity>> queryProductAttributeValues(int productId) {
        Map<Integer, Map<Integer, ProductAttributeValueEntity>> productCheckedAttributeValueMap = new HashMap<>();
        List<ProductAttributeValueEntity> productAttributeValueEntities = productRepository.queryProductAttributeValues(productId);
        if (null != productAttributeValueEntities) {
            for (ProductAttributeValueEntity entity : productAttributeValueEntities) {
                Map<Integer, ProductAttributeValueEntity> map = productCheckedAttributeValueMap.get(entity.getAttributeId());
                if (null == map) {
                    map = new HashMap<>();
                    productCheckedAttributeValueMap.put(entity.getAttributeId(), map);
                }
                map.put(entity.getAttributeValueId(), entity);
            }
        }
        return productCheckedAttributeValueMap;
    }

    @Transactional
    public List<ProductAttribute> saveProductAttributes(int productId, List<ProductAttributeValueParam> productAttributeValueParams) {
        ProductEntity productEntity = productRepository.queryProduct(productId);
        if (null == productEntity) {
            throw new ServiceException(StatusCode.ResourceNotFound);
        }

        Map<Integer, AttributeEntity> attributeEntityMap = attributeService.queryAttributeEntities(
                productAttributeValueParams
                        .stream()
                        .mapToInt(ProductAttributeValueParam::getAttributeId)
                        .toArray()
        ).stream().collect(Collectors.toMap(AttributeEntity::getId, p->p));

        for (ProductAttributeValueParam param : productAttributeValueParams) {
            if (!attributeEntityMap.containsKey(param.getAttributeId())) {
                 throw new ValidationException("attributeId="+param.getAttributeId(), ErrorCode.FIELD_INVALID);
            }

            Set<Integer> values = attributeService.queryAttributeValues(param.getAttributeId())
                    .stream().map(AttributeValueEntity::getId).collect(Collectors.toSet());

            if (!values.contains(param.getValueId())) {
                throw new ValidationException("valueId="+param.getValueId(), ErrorCode.FIELD_INVALID);
            }
        }

        productRepository.deleteProductAttributes(productId);
        for (ProductAttributeValueParam param : productAttributeValueParams) {
            ProductAttributeValueEntity entity = new ProductAttributeValueEntity();
            entity.setAttributeId(param.getAttributeId());
            entity.setProductId(productId);
            entity.setAttributeValueId(param.getValueId());
            productRepository.addProductAttributeValue(entity);
        }

        return queryCheckedProductAttributes(productId);
    }

    public List<ProductAttribute> queryAllProductAttributes(int productId) {
        ProductEntity productEntity = productRepository.queryProduct(productId);
        if (null == productEntity) {
            throw new ServiceException(StatusCode.ResourceNotFound);
        }

        List<Category> categories = categoryService.getParentCategories(productEntity.getCategoryId());
        return queryAllProductAttributes(productId, categories);
    }

    private List<ProductAttribute> queryAllProductAttributes(int productId, List<Category> categories) {
        Map<Integer, Map<Integer, ProductAttributeValueEntity>> productCheckedAttributeValueMap = queryProductAttributeValues(productId);

        Map<Integer, ProductAttribute> productAttributeMap = new HashMap<>();
        for (Category category : categories) {
            List<Attribute> attributeList = categoryService.queryCategoryAttributes(category.getId());
            for (Attribute attribute : attributeList) {
                ProductAttribute productAttribute = new ProductAttribute();
                productAttribute.setAttributeId(attribute.getId());
                productAttribute.setName(attribute.getName());
                productAttribute.setDescription(attribute.getDescription());

                Map<Integer, ProductAttributeValueEntity> productCheckedValueMap = productCheckedAttributeValueMap.get(attribute.getId());
                if (CollectionUtils.isNotEmpty(attribute.getValues())) {
                    for (Attribute.Value value : attribute.getValues()) {
                        ProductAttribute.Value paValue = new ProductAttribute.Value();
                        paValue.setValueId(value.getId());
                        paValue.setValue(value.getValue());
                        if (null != productCheckedValueMap) {
                            ProductAttributeValueEntity entity = productCheckedValueMap.get(value.getId());
                            if (null != entity) {
                                paValue.setId(entity.getId());
                                paValue.setChecked(true);
                            }
                        }
                        productAttribute.addValue(paValue);
                    }
                } else {
                    productAttribute.setValues(convert(productCheckedValueMap.values()));
                }

                productAttributeMap.put(attribute.getId(), productAttribute);
            }
        }
        return new ArrayList<>(productAttributeMap.values());
    }

    private List<ProductAttribute.Value> convert(Collection<ProductAttributeValueEntity> valueEntities) {
        List<ProductAttribute.Value> values = new ArrayList<>();
        for (ProductAttributeValueEntity entity : valueEntities) {
            ProductAttribute.Value value = new ProductAttribute.Value(entity);
            values.add(value);
        }
        return values;
    }

    public Product convert(ProductEntity productEntity) {
        Product product = new Product();
        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setCode(productEntity.getCode());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());
        product.setCurrency(productEntity.getCurrency());
        product.setVendorId(productEntity.getVendorId());
        product.setStoreId(productEntity.getStoreId());
        product.setInventory(productEntity.getInventory());
        List<Category> categories = categoryService.getParentCategories(productEntity.getCategoryId());
        product.setCategories(categories);
        product.setAttributes(queryCheckedProductAttributes(product.getId()));
        return product;
    }
}
