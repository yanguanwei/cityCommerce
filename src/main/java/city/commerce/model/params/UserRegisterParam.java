package city.commerce.model.params;

import city.commerce.model.UserType;
import city.commerce.model.error.*;
import city.commerce.validation.Validation;
import city.commerce.validation.Validator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-02
 */
@Data
public class UserRegisterParam implements Validator {
    @ApiModelProperty(required = true)
    private UserType userType;

    @ApiModelProperty(value = "email or phone or username", required = true)
    private String name;

    @ApiModelProperty(required = true)
    private String password;

    @Override
    public void validate(Validation validation) {
        String name = getName();
        if (StringUtils.isBlank(name)) {
            validation.add("name", ErrorCode.FIELD_REQUIRED);
        }

        if (StringUtils.isBlank(getPassword())) {
            validation.add("password", ErrorCode.FIELD_REQUIRED);
        }

        if (null == getUserType()) {
            validation.add("userType", ErrorCode.FIELD_REQUIRED);
        }
    }
}
