package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
@Data
public class ProductSkuEntity {
    private int id;
    private int productId;

    private String code;
    private String name;
    private String description;

    private int status;
}
