package city.commerce.repository.mapper;

import city.commerce.entity.ProductEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-26
 */
@Mapper
public interface ProductMapper {
    @Select("select * from product")
    List<ProductEntity> all();

    @Select("select * from Product where storeId=#{storeId}")
    List<ProductEntity> findByStoreId(int storeId);

    @Select(("select * from Product where id=#{id}"))
    ProductEntity findById(int id);

    @InsertProvider(type = Mappers.class, method = "product_insert")
    @Options(useGeneratedKeys=true)
    void add(ProductEntity productEntity);

    @InsertProvider(type = Mappers.class, method = "product_update")
    void update(ProductEntity productEntity);

    @Delete("delete from Product where id=#{id}")
    void delete(int id);
}
