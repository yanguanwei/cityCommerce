package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-31
 */
@Data
public class AuthorityEntity {
    private int id;
    private String appKey;
    private String appSecret;
    private int status;
    private int sign_verify;

    public boolean isVerifySign() {
        return sign_verify == 1;
    }
}
