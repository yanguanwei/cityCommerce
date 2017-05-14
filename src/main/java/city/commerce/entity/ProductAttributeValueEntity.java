package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
@Data
public class ProductAttributeValueEntity {
    private int id;
    private int skuId;
    private int productId;
    private int attributeId;
    private int attributeValueId;
}
