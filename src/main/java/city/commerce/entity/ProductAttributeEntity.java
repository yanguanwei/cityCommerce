package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-22
 */
@Data
public class ProductAttributeEntity {
    private int id;
    private int productId;
    private int attributeId;
    private int attributeValueId;
    private String value;
}
