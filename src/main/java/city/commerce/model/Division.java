package city.commerce.model;

import city.commerce.entity.DivisionEntity;
import city.commerce.validation.Validating;
import city.commerce.validation.Validation;
import city.commerce.validation.Validator;
import city.commerce.validation.Validators;
import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
@Data
public class Division implements Validator {
    private int id;
    private String name;
    private int parentId;
    private DivisionLevel level;

    public Division(DivisionEntity divisionEntity) {
        this.id = divisionEntity.getId();
        this.name = divisionEntity.getName();
        this.parentId = divisionEntity.getParentId();
        this.level = DivisionLevel.of(divisionEntity.getLevel());
    }

    public DivisionEntity toEntity() {
        DivisionEntity divisionEntity = new DivisionEntity();
        divisionEntity.setId(id);
        divisionEntity.setName(divisionEntity.getName());
        divisionEntity.setLevel(level.getLevel());
        divisionEntity.setParentId(parentId);

        return divisionEntity;
    }

    @Override
    public void validate(Validation validation) {
        Validating.of(validation, "name", getName()).validating(Validators::required);
        Validating.of(validation, "level", getLevel()).validating(Validators::required);
    }
}
