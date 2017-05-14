package city.commerce.repository.mapper;

import city.commerce.entity.CategoryAttributeEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-08
 */
@Mapper
public interface CategoryAttributeMapper {
    @Select("select * from CategoryAttribute where categoryId=#{categoryId}")
    List<CategoryAttributeEntity> findAllByCategoryId(int categoryId);

    @Select("select * from CategoryAttribute where categoryId=#{arg0} and attributeId=#{arg1}")
    CategoryAttributeEntity findByCategoryIdAndAttributeId(int categoryId, int attributeId);

    @Insert("insert into CategoryAttribute(categoryId,attributeId) VALUES(#{categoryId},#{attributeId})")
    @Options(useGeneratedKeys=true)
    void add(CategoryAttributeEntity categoryAttributeEntity);

    @Delete("delete CategoryAttribute from CategoryAttribute where id=#{id}")
    void delete(int id);

    @Delete("delete CategoryAttribute from CategoryAttribute where categoryId=#{arg0} and attributeId=#{arg1}")
    void deleteByCategoryAndAttribute(int categoryId, int attributeId);

    @Delete("delete CategoryAttribute from CategoryAttribute where categoryId=#{categoryId}")
    void deleteByCategoryId(int categoryId);

    @Delete("delete CategoryAttribute from CategoryAttribute where attributeId=#{attributeId}")
    void deleteByAttributeId(int attributeId);
}
