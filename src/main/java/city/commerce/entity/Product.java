package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-15
 */
@Data
public class Product {
    private int id;
    private int storeId;
    private int vendorId;
    private int categoryId;
    private Category category;
    private String name;
    private String description;
    private int price;
    private String currency;
}
