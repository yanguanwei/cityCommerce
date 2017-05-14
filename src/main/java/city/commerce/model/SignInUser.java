package city.commerce.model;

import city.commerce.entity.UserEntity;
import lombok.Getter;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-01
 */
public class SignInUser extends User {
    @Getter private String token;

    public SignInUser() {
    }

    public SignInUser(UserEntity userEntity, String token) {
        super(userEntity);
        this.token = token;
    }
}
