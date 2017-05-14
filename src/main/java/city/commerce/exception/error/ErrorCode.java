package city.commerce.exception.error;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
public class ErrorCode {
    private String code;
    private String message;

    public ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorCode of(String code, String message) {
        return new ErrorCode(code, message);
    }
}
