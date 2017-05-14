package city.commerce.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-22
 */
@Data
public class UserEntity {
    private int id;
    private int type;
    private long permissions;
    private String username;
    private String email;
    private String phone;
    private String password;

    private int created;
}
