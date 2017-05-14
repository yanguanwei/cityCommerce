package city.commerce.model;

import city.commerce.entity.UserEntity;
import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-31
 */
@Data
public class User {
    private int userId;
    private UserType type;
    private Permission permission;
    private String username;
    private String phone;
    private String email;
    private String password;

    public User() {
    }

    public User(UserEntity userEntity) {
        this.userId = userEntity.getId();
        this.username = userEntity.getUsername();
        this.type = UserType.of(userEntity.getType());
        this.phone = userEntity.getPhone();
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
        this.permission = new Permission(userEntity.getPermissions());
    }

    public static User of(UserEntity userEntity) {
        return new User(userEntity);
    }
}
