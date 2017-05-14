package city.commerce.repository;

import city.commerce.entity.UserEntity;
import city.commerce.entity.UserProfileEntity;
import city.commerce.model.User;
import city.commerce.repository.mapper.UserMapper;
import city.commerce.repository.mapper.UserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-31
 */
@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    public UserEntity queryUserById(int id) {
        return userMapper.findById(id);
    }

    public UserEntity queryUserByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public UserEntity queryUserByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    public UserEntity queryUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public UserEntity addUser(UserEntity userEntity) {
        userMapper.add(userEntity);
        return userEntity;
    }

    public UserProfileEntity queryUserProfile(int userId) {
        return userProfileMapper.findByUserId(userId);
    }

    public UserProfileEntity save(UserProfileEntity userProfileEntity) {
        UserProfileEntity exist = queryUserProfile(userProfileEntity.getUserId());
        if (null != exist) {
            userProfileMapper.update(userProfileEntity);
        } else {
            userProfileMapper.add(userProfileEntity);
        }

        return userProfileEntity;
    }
}
