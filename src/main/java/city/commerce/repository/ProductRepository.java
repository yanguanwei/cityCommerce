package city.commerce.repository;

import city.commerce.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-15
 */
@Mapper
public interface ProductRepository {

    @Select("select * from product")
    List<Product> all();
}
