package city.commerce.repository.mapper;

import city.commerce.entity.StoreEntity;
import city.commerce.model.Store;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@Mapper
public interface StoreMapper {

    @Insert("insert into Store(name,divisionId,placeId,longitude,latitude,street,created) VALUES(#{name},#{divisionId},#{placeId},#{longitude},#{latitude},#{street},#{created})")
    @Options(useGeneratedKeys=true)
    void add(StoreEntity storeEntity);

    @Update("update Store set name#{name},divisionId=#{divisionId},placeId=#{placeId},longitude=#{longitude},latitude=#{latitude},street=#{street},created=#{created} where id=#{id}")
    void update(StoreEntity storeEntity);

    @Select("select * from Store where id=#{id}")
    StoreEntity findById(int id);


    @Select("select * from Store where placeId=#{placeId}")
    StoreEntity findByPlaceId(String placeId);

    @Select("select * from Store")
    List<StoreEntity> all();
}
