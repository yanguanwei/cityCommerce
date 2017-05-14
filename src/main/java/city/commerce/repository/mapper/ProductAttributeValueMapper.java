package city.commerce.repository.mapper;

import city.commerce.entity.ProductAttributeValueEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-20
 */
public interface ProductAttributeValueMapper {

    @InsertProvider(type = Mappers.class, method = "product_attribute_value_insert")
    @Options(useGeneratedKeys=true)
    void add(ProductAttributeValueEntity productAttributeValueEntity);

    @InsertProvider(type = Mappers.class, method = "product_attribute_value_update")
    void update(ProductAttributeValueEntity productAttributeValueEntity);

    @Select("select * from ProductAttributeValue where productId=#{productId}")
    List<ProductAttributeValueEntity> findByProductId(int productId);

    @Select("select * from ProductAttributeValue where productId=#{arg0} and skuId=#{arg1}")
    List<ProductAttributeValueEntity> findBySkuId(int productId, int skuId);

    @Select("delete from ProductAttributeValue where productId=#{productId} and sku=0")
    void deleteByProduct(int productId);
}
