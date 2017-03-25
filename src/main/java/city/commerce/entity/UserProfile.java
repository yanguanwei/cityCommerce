package city.commerce.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-22
 */
@Data
public class UserProfile {
    private int userId;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String gender;
    private String avatar;
}
