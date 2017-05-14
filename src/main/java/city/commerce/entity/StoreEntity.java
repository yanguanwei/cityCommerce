package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-22
 */
@Data
public class StoreEntity {
    private int id;
    private String name;
    private int vendorId;
    private int divisionId;
    private String placeId;
    private String longitude;
    private String latitude;
    private String street;
    private int created;
}
