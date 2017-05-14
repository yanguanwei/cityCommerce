package city.commerce.model.error;

import city.commerce.model.Message;
import city.commerce.model.api.StatusCode;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-31
 */
public class ServiceException extends RuntimeException {
    private StatusCode statusCode;
    private List<? extends Error> errors;

    public ServiceException(StatusCode statusCode, Object...params) {
        super(Message.of(statusCode.name(), params));
        this.statusCode = statusCode;
    }

    public void setErrors(List<? extends Error> errors) {
        this.errors = errors;
    }

    public ServiceException(StatusCode statusCode, ErrorCode errorCode, Object...params) {
        super(Message.of(statusCode.name(), params));
        this.statusCode = statusCode;
        this.errors = Lists.newArrayList(new Error(errorCode));
    }

    public List<? extends Error> getErrors() {
        return errors;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
