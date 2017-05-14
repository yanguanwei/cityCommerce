package city.commerce.repository.mapper;

import city.commerce.entity.AttributeValueEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-31
 */
@Mapper
public interface AttributeValueMapper {

    @Select(("select * from AttributeValue"))
    List<AttributeValueEntity> all();

    @Select("select * from AttributeValue where attributeId=#{attributeId}")
    List<AttributeValueEntity> findAllByAttributeId(int attributeId);

    @Insert("insert into AttributeValue(value,attributeId) VALUES(#{value},#{attributeId})")
    @Options(useGeneratedKeys=true)
    void add(AttributeValueEntity attributeValueEntity);

    @Update("update AttributeValue set value=#{value} where id=#{id}")
    void update(AttributeValueEntity attributeValueEntity);

    @Delete("deleteCategoryAttribute from AttributeValue where id=#{id}")
    void delete(int id);

    @Delete("deleteCategoryAttribute from AttributeValue where attributeId=#{attributeId}")
    void deleteByAttributeId(int attributeId);
}
