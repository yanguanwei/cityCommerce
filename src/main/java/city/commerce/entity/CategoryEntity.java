package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-22
 */
@Data
public class CategoryEntity implements Comparable<CategoryEntity> {
    private int id;
    private String name;
    private String description;
    private int parentId;
    private int sequence;

    @Override
    public int compareTo(CategoryEntity o) {
        return sequence < o.getSequence() ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CategoryEntity) {
            return ((CategoryEntity) o).getId() == this.id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
