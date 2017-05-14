package city.commerce.repository.mapper;

import city.commerce.entity.AuthorityEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-31
 */
public interface AuthorityMapper {

    @Select("select * from Authority where appKey=#{appKey}")
    AuthorityEntity findByAppKey(String appKey);

    @Insert("insert into Authority(appKey,appSecret,status) VALUES(#{appKey},#{appSecret}, #{status})")
    @Options(useGeneratedKeys=true)
    void add(AuthorityEntity authorityEntity);

    @Update("update Authority set appKey=#{appKey}, appSecret=#{appSecret}, status=#{status} where id=#{id}")
    void update(AuthorityEntity authorityEntity);
}
