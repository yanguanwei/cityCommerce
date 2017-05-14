package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-15
 */
@Data
public class ProductEntity {
    private int id;
    private int storeId;
    private int vendorId;
    private int categoryId;
    private String code;
    private String name;
    private String description;
    private String currency;
    private int price;
    private int inventory;

    private String attributes;

    private int created;
    private int modified;

    private int status;
}
