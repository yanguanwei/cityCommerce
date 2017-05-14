package city.commerce.exception.error;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-28
 */

public class ErrorMessage {
    private String code;
    private String message;
    private String cause;

    public ErrorMessage(String code, String message, String cause) {
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getCause() {
        return cause;
    }
}
