package city.commerce.repository.mapper;

import city.commerce.entity.CategoryEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@Mapper
public interface CategoryMapper {

    @Select("select * from Category")
    List<CategoryEntity> all();

    @Insert("insert into Category(name,description,parentId,sequence) VALUES(#{name},#{description}, #{parentId},#{sequence})")
    @Options(useGeneratedKeys=true)
    void add(CategoryEntity categoryEntity);

    @Update("update Category set name=#{name}, description=#{description} where id=#{id}")
    void update(CategoryEntity categoryEntity);

    @Update("update Category set sequence=#{sequence} where id=#{id}")
    void updateSequence(int id, int sequence);

    @Update("update Category set parentId=#{parentId} where id=#{id}")
    void updateParent(int id, int parentId);

    @Update("update Category set parentId=#{arg1} where parentId=#{arg0}")
    void updateParentByParentId(int originParentId, int parentId);

    @Delete("deleteCategoryAttribute from Category where id=#{id}")
    void delete(int id);
}
