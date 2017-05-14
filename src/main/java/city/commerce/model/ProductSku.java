package city.commerce.model;

import lombok.Data;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
@Data
public class ProductSku {
    private int id;
    private int productId;
    private String code;
    private String name;
    private String description;
    private ProductStatus status;
    private int inventory;

    private List<ProductAttribute> attributes;
}
