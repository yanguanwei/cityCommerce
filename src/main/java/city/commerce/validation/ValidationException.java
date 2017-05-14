package city.commerce.validation;

import city.commerce.model.api.StatusCode;
import city.commerce.model.error.ErrorCode;
import city.commerce.model.error.ServiceException;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-03
 */
public class ValidationException extends ServiceException {

    public ValidationException(List<FieldError> errors) {
        super(StatusCode.RequestParameterInvalid);
        this.setErrors(errors);
    }

    public ValidationException(String field, ErrorCode code) {
        super(StatusCode.RequestParameterInvalid);
        this.setErrors(Lists.newArrayList(new FieldError(field, code)));
    }
}
