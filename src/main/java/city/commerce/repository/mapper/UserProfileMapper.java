package city.commerce.repository.mapper;

import city.commerce.entity.UserProfileEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-05
 */
public interface UserProfileMapper {
    @Insert("insert into UserProfile(userId,firstName,lastName,birthday,gender,avatar) VALUES(#{userId},#{firstName},#{lastName},#{birthday},#{gender},#{avatar})")
    void add(UserProfileEntity userProfileEntity);

    @Insert("update UserProfile set firstName=#{firstName},lastName=#{lastName},birthday=#{birthday},gender=#{gender},avatar=#{avatar}) where userId=#{userId}")
    void update(UserProfileEntity userProfileEntity);

    @Select("select * from UserProfile where userId=#{userId}")
    UserProfileEntity findByUserId(int userId);
}
