package city.commerce.model.params;

import city.commerce.model.ProductStatus;
import city.commerce.validation.Validating;
import city.commerce.validation.Validation;
import city.commerce.validation.Validator;
import city.commerce.validation.Validators;
import lombok.Data;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-05-03
 */
@Data
public class AddProductSkuParam implements Validator {
    private int productId;

    private String code;
    private String name;
    private String description;
    private ProductStatus status;
    private int inventory;

    List<ProductAttributeValueParam> attributes;

    @Override
    public void validate(Validation validation) {
        Validating.of(validation, "productId", getProductId()).validating(Validators::required);
        Validating.of(validation, "code", getCode()).validating(Validators::required);
        Validating.of(validation, "name", getName()).validating(Validators::required);

        Validating.of(validation, "status", getStatus()).validating(Validators::required);
        Validating.of(validation, "inventory", getInventory()).validating(Validators::required);
        Validating.of(validation, "attributes", getAttributes()).validating(Validators::required);
    }
}
