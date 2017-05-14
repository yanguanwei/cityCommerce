package city.commerce.repository.mapper;

import city.commerce.entity.ProductEntity;
import city.commerce.entity.ProductSkuEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
@Mapper
public interface ProductSkuMapper {

    @Select("select * from ProductSku where productId=#{productId}")
    List<ProductEntity> findByProductId(int productId);

    @InsertProvider(type = Mappers.class, method = "product_sku_insert")
    @Options(useGeneratedKeys=true)
    void add(ProductSkuEntity productSkuEntity);


    @UpdateProvider(type = Mappers.class, method = "product_sku_update")
    void update(ProductSkuEntity productSkuEntity);

    @Delete("delete from ProductSku where id=#{id}")
    void delete(int id);

    @Delete("delete from ProductSku where productId=#{productId}")
    void deleteByProductId(int productId);
}
