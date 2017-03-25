package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-16
 */
@Data
public class Address {
    private int id;
    private AddressType addressType;
    private Division division;
    private double longitude;
    private double latitude;
    private String street;
}
