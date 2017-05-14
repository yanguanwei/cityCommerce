package city.commerce.model;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
@Data
public class Location {
    private double longitude;
    private double latitude;

    public Location() {
    }

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
