package city.commerce.model;

import lombok.Data;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-26
 */
@Data
public class Store {
    private int id;
    private String name;

    private int vendorId;

    private String placeId;

    private int divisionId;
    private Location location;
    private String street;
    private List<Division> divisions;

    private int created;
}
