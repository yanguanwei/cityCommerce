package city.commerce.validation;

import city.commerce.model.error.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-03
 */
public class Validation {
    private List<FieldError> errors = new ArrayList<>();

    public Validation add(String field, ErrorCode errorCode) {
        FieldError fieldError = new FieldError(field, errorCode);
        this.errors.add(fieldError);
        return this;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public static void validate(Validator validator) throws ValidationException {
        Validation validation = new Validation();
        validator.validate(validation);
        if (validation.hasErrors()) {
            throw new ValidationException(validation.getErrors());
        }
    }
}
