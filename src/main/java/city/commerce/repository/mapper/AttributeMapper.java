package city.commerce.repository.mapper;

import city.commerce.entity.AttributeEntity;
import city.commerce.entity.AuthorityEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-31
 */
@Mapper
public interface AttributeMapper {

    @Select("select * from Attribute")
    List<AttributeEntity> all();

    @Insert("insert into Attribute(name,description) VALUES(#{name},#{description})")
    @Options(useGeneratedKeys=true)
    void add(AttributeEntity attributeEntity);

    @Update("update Attribute set name=#{name}, description=#{description} where id=#{id}")
    void update(AttributeEntity attributeEntity);

    @Delete("deleteCategoryAttribute from Attribute where id=#{id}")
    void delete(int id);
}
