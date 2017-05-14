package city.commerce.controller.api.response;

import city.commerce.model.api.StatusCode;
import city.commerce.exception.error.ErrorMessage;
import city.commerce.model.Message;
import city.commerce.model.error.Error;
import city.commerce.model.error.ServiceException;
import city.commerce.validation.ValidationException;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-28
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<Result> {

    private int status;

    private String code;

    private String message;

    private List<? extends Error> errors;

    private Result result;

    public Response(StatusCode statusCode) {
        this.status = statusCode.getStatus();
        this.code = statusCode.getCode();

        if (statusCode != StatusCode.OK) {
            this.message = Message.of(statusCode.getCode());
        }
    }

    public Response(StatusCode statusCode, Result result) {
        this(statusCode);
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<? extends Error> getErrors() {
        return errors;
    }

    public int getStatus() {
        return status;
    }

    public Result getResult() {
        return result;
    }

    public static <Result> Response<Result> of(ServiceException serviceException) {
        Response<Result> response = of(serviceException.getStatusCode());

        if (serviceException instanceof ValidationException) {
            ValidationException validationException = (ValidationException) serviceException;
            response.errors = validationException.getErrors();
        }

        return response;
    }

    public static <Result> Response<Result> of(StatusCode statusCode) {
        return new Response<Result>(statusCode);
    }

    public static <Result> Response<Result> ok() {
        return new Response<Result>(StatusCode.OK, null);
    }

    public static <Result> Response<Result> ok(Result result) {
        return new Response<Result>(StatusCode.OK, result);
    }
}
