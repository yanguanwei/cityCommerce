package city.commerce.entity;

import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-18
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

    @Override
    public String toString() {
        return "Location["+longitude+", "+latitude+"]";
    }
}
