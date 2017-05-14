package city.commerce.service;

import city.commerce.entity.UserEntity;
import city.commerce.entity.UserProfileEntity;
import city.commerce.model.*;
import city.commerce.model.error.ErrorCode;
import city.commerce.model.params.UserRegisterParam;
import city.commerce.repository.UserRepository;
import city.commerce.validation.Validation;
import city.commerce.validation.ValidationException;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-01
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserProfile queryUserProfile(int userId) {
        UserProfileEntity userProfileEntity = userRepository.queryUserProfile(userId);
        if (null != userProfileEntity) {
            return new UserProfile(userProfileEntity);
        }
        return new UserProfile();
    }

    public UserProfile saveUserProfile(int userId, UserProfile userProfile) {
        Validation.validate(userProfile);

        UserProfileEntity userProfileEntity = new UserProfileEntity();
        userProfileEntity.setUserId(userId);
        userProfileEntity.setFirstName(userProfile.getFirstName());
        userProfileEntity.setLastName(userProfile.getLastName());
        userProfileEntity.setAvatar(userProfile.getAvatar());
        userProfileEntity.setBirthday(userProfile.getBirthday());
        userProfileEntity.setGender(userProfile.getGender().ordinal());

        return new UserProfile(userRepository.save(userProfileEntity));
    }

    public UserEntity queryUser(String name) {
        UserEntity userEntity = null;
        if (name.contains("@")) {
            userEntity = userRepository.queryUserByEmail(name);
        } else if (StringUtils.isNumeric(name)) {
            userEntity = userRepository.queryUserByPhone(name);
        } else {
            userEntity = userRepository.queryUserByUsername(name);
        }
        return userEntity;
    }

    public UserEntity queryUser(int userId) {
        return userRepository.queryUserById(userId);
    }

    public UserEntity register(UserRegisterParam userRegisterParam) {
        String name = userRegisterParam.getName();
        UserEntity userEntity = queryUser(name);
        if (null != userEntity) {
            throw new ValidationException("name", ErrorCode.FIELD_EXISTED);
        }

        userEntity = new UserEntity();
        if (name.contains("@")) {
            userEntity.setEmail(name);
        } else if (StringUtils.isNumeric(name)) {
            userEntity.setPhone(name);
        } else {
            userEntity.setUsername(name);
        }

        userEntity.setType(userRegisterParam.getUserType().getType());
        userEntity.setPermissions(initPermission(userRegisterParam.getUserType()));
        userEntity.setPassword(encryptPassword(userRegisterParam.getPassword()));
        userEntity.setCreated((int) (System.currentTimeMillis() / 1000));

        return userRepository.addUser(userEntity);
    }

    private long initPermission(UserType userType) {
        long permissions = 0;
        switch (userType) {
            case Vendor:
                permissions = (permissions | Permission.Vendor);
                break;
            case Buyer:
                permissions = (permissions | Permission.Buyer);
                break;
            case Admin:
                permissions = (permissions | Permission.Admin);
                break;
        }

        return permissions;
    }

    public String encryptPassword(String password) {
        return Hashing.md5().newHasher()
                .putString("city", UTF_8)
                .putString(password, UTF_8)
                .putString("Commerce", UTF_8)
                .hash().toString();
    }
}
