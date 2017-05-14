package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-22
 */
@Data
public class UserProfileEntity {
    private int userId;
    private String firstName;
    private String lastName;
    private String birthday;
    private int gender;
    private String avatar;
}
