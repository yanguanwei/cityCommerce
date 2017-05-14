package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-26
 */
@Data
public class IndexStoreProduct {
    private int productId;
    private int storeId;
    private String productName;
    private String storeName;
    private double longitude;
    private double latitude;
}
