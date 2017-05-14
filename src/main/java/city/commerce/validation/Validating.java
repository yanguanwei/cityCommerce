package city.commerce.validation;

import city.commerce.model.error.ErrorCode;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-15
 */
public class Validating {
    private Validation validation;
    private String field;

    private Object value;
    private boolean valid;

    private Validating(Validation validation, String field, Object value) {
        this.validation = validation;
        this.field = field;
        this.value = value;
    }

    public void addError(ErrorCode errorCode) {
        this.validation.add(field, errorCode);
    }

    public Object getValue() {
        if (null == value) {
            return null;
        }

        if (value instanceof Supplier) {
            return ((Supplier) value).get();
        }

        return value;
    }

    @SafeVarargs
    public final Validating validating(Function<Validating, Boolean>... steps) {
        for (Function<Validating, Boolean> step : steps) {
            if (!step.apply(this)) {
                valid = false;
                break;
            }
        }

        return this;
    }

    public Validating then(String field, Object value, Consumer<Validating> validating) {
        if (valid) {
            validating.accept(new Validating(validation, field, value));
        }
        return this;
    }

    public Validating then(String field, Supplier<Object> value, Consumer<Validating> validating) {
        if (valid) {
            validating.accept(new Validating(validation, field, value));
        }
        return this;
    }

    public static Validating of(Validation validation, String field, Object value) {
        return new Validating(validation, field, value);
    }
}
