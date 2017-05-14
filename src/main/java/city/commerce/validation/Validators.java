package city.commerce.validation;

import city.commerce.model.error.ErrorCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-15
 */
public class Validators {
    public static boolean required(Validating validating) {
        Object value = validating.getValue();
        boolean valid = true;

        if (value instanceof String) {
            if (StringUtils.isBlank((String) value)) {
                valid = false;
            }
        } else if (value instanceof Integer) {
            if ((Integer) value <= 0) {
                valid = false;
            }
        } else if (value instanceof Long) {
            if ((Long) value <= 0) {
                valid = false;
            }
        } else  {
            if (null == value) {
                valid = false;
            }
        }

        if (!valid) {
            validating.addError(ErrorCode.FIELD_REQUIRED);
        }

        return valid;
    }

    public static boolean numberic(Validating validating) {
        Object value = validating.getValue();
        boolean valid = true;

        if ((value instanceof Short) ||
                (value instanceof Integer) ||
                (value instanceof Long)) {
            return true;
        }

        if (value instanceof String) {
            if (!StringUtils.isNumeric((String) value)) {
                valid = false;
            }
        }

        if (!valid) {
            validating.addError(ErrorCode.FIELD_INVALID_FORMATTER);
        }

        return valid;
    }
}
