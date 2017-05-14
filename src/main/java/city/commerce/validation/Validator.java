package city.commerce.validation;

import city.commerce.model.error.ErrorCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-03
 */
public interface Validator {
    void validate(Validation validation);

    default boolean validateRequired(String field, String value, Validation validation) {
        if (StringUtils.isBlank(value)) {
            validation.add(field, ErrorCode.FIELD_REQUIRED);
            return false;
        }

        return true;
    }

    default boolean validateRequired(String field, Object value, Validation validation) {
        if (value  == null) {
            validation.add(field, ErrorCode.FIELD_REQUIRED);
            return false;
        }

        return true;
    }

    default boolean validateValid(String field, int value, Validation validation) {
        if (value <= 0) {
            validation.add(field, ErrorCode.FIELD_INVALID_FORMATTER);
            return false;
        }

        return true;
    }

    default boolean validateNumeric(String field, String value, Validation validation) {
        if (StringUtils.isNumeric(value)) {
            return true;
        }

        validation.add(field, ErrorCode.FIELD_INVALID_FORMATTER);
        return false;
    }
}
