package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-22
 */
@Data
public class AttributeValueEntity {
    private int id;
    private int attributeId;
    private String value;
}
