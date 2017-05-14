package city.commerce.repository;

import city.commerce.entity.ProductSkuEntity;
import city.commerce.repository.mapper.ProductSkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author young.alway@gmail.com
 * @since 2017-05-03
 */
@Repository
public class ProductSkuRepository {
    @Autowired
    private ProductSkuMapper productSkuMapper;

    public ProductSkuEntity addSku(ProductSkuEntity entity) {
        productSkuMapper.add(entity);
        return entity;
    }

    public ProductSkuEntity updateSku(ProductSkuEntity entity) {
        productSkuMapper.update(entity);
        return entity;
    }

    public void deleteSku(int skuId) {
        productSkuMapper.delete(skuId);
    }

    public void deleteAll(int productId) {
        productSkuMapper.deleteByProductId(productId);
    }
}
