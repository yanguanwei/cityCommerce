package city.commerce.model.params;

import city.commerce.model.Currency;
import city.commerce.model.ProductStatus;
import city.commerce.validation.Validating;
import city.commerce.validation.Validation;
import city.commerce.validation.Validator;
import city.commerce.validation.Validators;
import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-16
 */
@Data
public class ProductParam implements Validator {
    private int id;
    private String code;
    private String name;
    private String description;

    private int price;
    private Currency currency;
    private int inventory;

    private int storeId;

    private ProductStatus status;

    private int categoryId;

    @Override
    public void validate(Validation validation) {
        Validating.of(validation, "name", getName()).validating(Validators::required);
        Validating.of(validation, "storeId", getStoreId()).validating(Validators::required);
        Validating.of(validation, "categoryId", getCategoryId()).validating(Validators::required);
        Validating.of(validation, "status", getStatus()).validating(Validators::required);
        Validating.of(validation, "currency", getCurrency()).validating(Validators::required);
        Validating.of(validation, "price", getPrice()).validating(Validators::required);
    }
}
