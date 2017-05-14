package city.commerce.model.params;

import city.commerce.entity.StoreEntity;
import city.commerce.validation.Validating;
import city.commerce.validation.Validation;
import city.commerce.validation.Validator;
import city.commerce.validation.Validators;
import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-12
 */
@Data
public class StoreParam implements Validator {
    private int id;
    private String name;
    private int vendorId;
    private int divisionId;
    private LocationParam location;
    private String street;

    @Override
    public void validate(Validation validation) {
        Validating.of(validation, "name", getName())
                .validating(Validators::required);

        Validating.of(validation, "vendorId", getVendorId())
                .validating(Validators::required);


        Validating.of(validation, "divisionId", getDivisionId())
                .validating(Validators::required);

        Validating.of(validation, "location", this.getLocation())
                .validating(Validators::required)
                .then("location.longitude", this.getLocation()::getLongitude, v -> {
                    v.validating(Validators::required, Validators::numberic);
                })
                .then("location.latitude", this.getLocation()::getLatitude, v -> {
                    v.validating(Validators::required, Validators::numberic);
                })
            ;

        Validating.of(validation, "street", this.getStreet())
                .validating(Validators::required);
    }

    public StoreEntity toStoreEntity() {
        StoreEntity entity = new StoreEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setVendorId(vendorId);
        entity.setDivisionId(divisionId);
        entity.setLongitude(location.getLongitude());
        entity.setLatitude(location.getLatitude());
        entity.setStreet(street);

        return entity;
    }
}
