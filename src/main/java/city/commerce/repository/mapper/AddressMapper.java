package city.commerce.repository.mapper;

import city.commerce.entity.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@Mapper
public interface AddressMapper {

    @Select("select * from Address where id=#{id}")
    Address find(int id);

    @Select("select * from Address where placeId=#{placeId}")
    Address findByPlaceId(String placeId);

    @Insert("insert into Address(divisionId,placeId,latitude,longitude,street) VALUES(#{divisionId},#{placeId}, #{latitude},#{longitude},#{street})")
    @Options(useGeneratedKeys=true)
    void add(Address address);
}
