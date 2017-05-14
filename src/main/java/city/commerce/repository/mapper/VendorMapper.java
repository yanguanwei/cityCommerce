package city.commerce.repository.mapper;

import city.commerce.entity.VendorEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-12
 */
@Mapper
public interface VendorMapper {

    @Select("select * from Vendor where userId=#{userId}")
    VendorEntity find(int userId);

    @Update(("update Vendor set level=#{level} where userId=#{userId}"))
    void update(VendorEntity vendorEntity);

    @Insert("insert into Vendor (userId, level) values (#{userId}, #{level})")
    void add(VendorEntity vendorEntity);
}
