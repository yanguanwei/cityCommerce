package city.commerce.repository.mapper;

import city.commerce.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-03
 */
@Mapper
public interface UserMapper {

    @Select(("select * from User where id=#{id}"))
    UserEntity findById(int id);

    @Select(("select * from User where username=#{username}"))
    UserEntity findByUsername(String username);

    @Select(("select * from User where email=#{email}"))
    UserEntity findByEmail(String email);

    @Select(("select * from User where phone=#{phone}"))
    UserEntity findByPhone(String phone);

    @Insert("insert into User(username,email,phone,type,permissions,password,created) VALUES(#{username},#{email},#{phone},#{type},#{permissions},#{password},#{created})")
    @Options(useGeneratedKeys=true)
    void add(UserEntity userEntity);
}
