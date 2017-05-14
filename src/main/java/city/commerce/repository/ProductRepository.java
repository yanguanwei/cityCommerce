package city.commerce.repository;

import city.commerce.entity.ProductAttributeValueEntity;
import city.commerce.entity.ProductEntity;
import city.commerce.repository.mapper.ProductAttributeValueMapper;
import city.commerce.repository.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-15
 */
@Repository
public class ProductRepository {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductAttributeValueMapper productAttributeValueMapper;

    public List<ProductEntity> queryProductsByStoreId(int storeId) {
        return productMapper.findByStoreId(storeId);
    }

    public ProductEntity queryProduct(int productId) {
        return productMapper.findById(productId);
    }

    public ProductEntity addProduct(ProductEntity productEntity) {
        productMapper.add(productEntity);
        return productEntity;
    }

    public ProductEntity updateProduct(ProductEntity productEntity) {
        productMapper.update(productEntity);
        return productEntity;
    }

    public ProductAttributeValueEntity addProductAttributeValue(ProductAttributeValueEntity entity) {
        productAttributeValueMapper.add(entity);
        return entity;
    }

    public ProductAttributeValueEntity updateProductAttributeValue(ProductAttributeValueEntity entity) {
        productAttributeValueMapper.update(entity);
        return entity;
    }

    public void deleteProductAttributes(int productId) {
        productAttributeValueMapper.deleteByProduct(productId);
    }

    public List<ProductAttributeValueEntity> queryProductAttributeValues(int productId) {
        return productAttributeValueMapper.findByProductId(productId);
    }
}
