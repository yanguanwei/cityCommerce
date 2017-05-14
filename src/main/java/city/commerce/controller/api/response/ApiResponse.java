package city.commerce.controller.api.response;

import city.commerce.exception.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<R> {
    private boolean success;

    private String errorCode;
    private String message;

    private R result;

    public static <R> ApiResponse<R> successOf(R result) {
        ApiResponse<R> response = new ApiResponse<>();
        response.success = true;
        response.result = result;
        return response;
    }

    public static <R> ApiResponse<R> errorOf(ErrorCode errorCode) {
        ApiResponse<R> response = new ApiResponse<>();
        response.success = false;
        response.errorCode = errorCode.getCode();
        response.message = errorCode.getMessage();

        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }
}
