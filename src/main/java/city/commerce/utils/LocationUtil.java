package city.commerce.utils;

import city.commerce.entity.Location;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-18
 */
public class LocationUtil {

    public static double getDistance(Location location1, Location location2) {
        return getDistance(
                location1.getLongitude(), location1.getLatitude(),
                location2.getLongitude(), location2.getLatitude()
        );
    }

    public static double getDistance(double long1, double lat1, double long2, double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }
}
