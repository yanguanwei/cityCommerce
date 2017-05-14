package city.commerce.model;

import city.commerce.entity.CategoryEntity;
import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
@Data
public class Category {
    private int id;
    private String name;
    private String description;

    public Category() {

    }

    public Category(CategoryEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }
}
