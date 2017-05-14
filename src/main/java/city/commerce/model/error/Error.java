package city.commerce.model.error;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-03
 */
public class Error {
    private ErrorCode code;

    public Error(ErrorCode code) {
        this.code = code;
    }

    public String toString() {
        return getCode().name();
    }

    public ErrorCode getCode() {
        return code;
    }
}
