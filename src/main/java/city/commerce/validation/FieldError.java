package city.commerce.validation;

import city.commerce.model.Message;
import city.commerce.model.error.Error;
import city.commerce.model.error.ErrorCode;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-03
 */
public class FieldError extends Error {
    private String field;

    public FieldError(String field, ErrorCode code) {
        super(code);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    @Override
    public String toString() {
        return Message.of(getCode().name(), field);
    }
}
