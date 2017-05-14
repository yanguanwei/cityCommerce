package city.commerce.model;

import city.commerce.entity.AttributeEntity;
import city.commerce.entity.AttributeValueEntity;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-08
 */
@Data
public class Attribute {

    @Data
    public static class Value {
        private int id;
        private String value;

        public Value() {
        }

        public Value(int id, String value) {
            this.id = id;
            this.value = value;
        }

        public Value(AttributeValueEntity attributeValueEntity) {
            this.id = attributeValueEntity.getId();
            this.value = attributeValueEntity.getValue();
        }
    }

    private int id;
    private String name;
    private String description;

    private List<Value> values = new ArrayList<>();
    private Set<Integer> valueSet = new HashSet<>();

    public Attribute() {
    }

    public Attribute(AttributeEntity attributeEntity) {
        this.id = attributeEntity.getId();
        this.name = attributeEntity.getName();
        this.description = attributeEntity.getDescription();
    }

    public void addValue(AttributeValueEntity attributeValueEntity) {
        this.values.add(new Value(attributeValueEntity));
        this.valueSet.add(attributeValueEntity.getId());
    }

    public boolean containValue(int valueId) {
        return this.valueSet.contains(valueId);
    }
}
