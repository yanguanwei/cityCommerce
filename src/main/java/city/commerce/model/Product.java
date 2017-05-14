package city.commerce.model;

import lombok.Data;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
@Data
public class Product {
    private int id;
    private String code;
    private String name;
    private String description;

    private int price;
    private String currency;
    private int inventory;

    private int storeId;
    private int vendorId;

    private ProductStatus status;
    private List<Category> categories;
    private List<ProductAttribute> attributes;

    private List<ProductSku> skus;
}
