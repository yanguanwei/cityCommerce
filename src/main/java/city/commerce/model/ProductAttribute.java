package city.commerce.model;

import city.commerce.entity.ProductAttributeValueEntity;
import lombok.Data;

import java.util.*;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
@Data
public class ProductAttribute {
    private int attributeId;
    private String name;
    private String description;

    @Data
    public static class Value {
        private int id;
        private int valueId;
        private String value;
        private boolean checked;

        public Value() {

        }

        public Value(ProductAttributeValueEntity entity) {
            this.id = entity.getId();
            this.valueId = entity.getAttributeValueId();
            this.checked = true;
        }

        public Value(ProductAttributeValueEntity entity, String value) {
            this(entity);
            this.value = value;
        }
    }

    private List<Value> values;
    private Map<Integer, Value> valueMap = new HashMap<>();

    public void addValue(Value value) {
        if (null == this.values) {
            this.values = new ArrayList<>();
        }
        this.values.add(value);
        this.valueMap.put(value.getId(), value);
    }

    public boolean containValue(int valueId) {
        return this.valueMap.containsKey(valueId);
    }

    public Value getValue(int valueId) {
        return this.valueMap.get(valueId);
    }
}
