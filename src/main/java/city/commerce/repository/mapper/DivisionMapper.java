package city.commerce.repository.mapper;

import city.commerce.entity.DivisionEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@Mapper
public interface DivisionMapper {
    @Select("select * from Division")
    List<DivisionEntity> all();

    @Select("select * from Division where id=#{id}")
    DivisionEntity find(int id);

    @Select("select * from Division where name=#{name}")
    DivisionEntity findByName(String name);

    @Insert("insert into Division(name,code,parentId,rootId,level) VALUES (#{name},#{code}, #{parentId},#{rootId},#{level})")
    @Options(useGeneratedKeys=true)
    void add(DivisionEntity divisionEntity);

    @UpdateProvider(type = Mappers.class, method = "division_update")
    void update(DivisionEntity divisionEntity);

    @Select("delete from Division where id=#{id}")
    void delete(int id);
}
