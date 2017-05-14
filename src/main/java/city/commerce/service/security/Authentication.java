package city.commerce.service.security;

import city.commerce.model.error.ErrorCode;
import city.commerce.validation.Validation;
import city.commerce.validation.Validator;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-30
 */
@Getter
public class Authentication implements Validator {
    private String appKey;
    private String signature;
    private int timestamp;

    public Authentication(String appKey, String signature, int timestamp) {
        this.appKey = appKey;
        this.signature = signature;
        this.timestamp = timestamp;
    }

    @Override
    public void validate(Validation validation) {
        if (StringUtils.isBlank(appKey)) {
            validation.add("appKey", ErrorCode.FIELD_REQUIRED);
        }
    }
}
