package city.commerce.model.params;

import city.commerce.validation.Validation;
import city.commerce.validation.Validator;
import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-01
 */
@Data
public class UserSignInParam implements Validator {
    private String name;
    private String password;

    @Override
    public void validate(Validation validation) {
        validateRequired("name", name, validation);
        validateRequired("password", password, validation);
    }
}
