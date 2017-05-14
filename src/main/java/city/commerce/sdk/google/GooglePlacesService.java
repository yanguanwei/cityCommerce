package city.commerce.sdk.google;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
public class GooglePlacesService {
    private static final String key = "AIzaSyBIyTVYvWiGwSBifu_7ioJkzOjOIn7r5GM";

    private static final String GOOGLE_PLACES_SERVICE_API = "https://maps.googleapis.com/maps/api/place/details/json";

    public static class Place {
        private String placeId;
        private String name;
        private double latitude;
        private double longitude;
        private String country;
        private String province;
        private String city;
        private String county;
        private String street;
        private String postalCode;

        public String getPlaceId() {
            return placeId;
        }

        public String getName() {
            return name;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public String getCountry() {
            return country;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public String getStreet() {
            return street;
        }

        public String getCounty() {
            return county;
        }

        public String getPostalCode() {
            return postalCode;
        }
    }

    public static Place getPlaceDetail(String placeId) {
        Place place = new Place();
        place.placeId = placeId;

        try {
            Connection.Response response = Jsoup.connect(GOOGLE_PLACES_SERVICE_API)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/json")
                    .data("key", key)
                    .data("placeid", placeId)
                    .execute();
            String body = response.body();
            if (null != body) {
                JSONObject json = (JSONObject) JSONObject.parse(body);
                if ("OK".equals(json.getString("status"))) {
                    JSONObject result = json.getJSONObject("result");

                    place.name = result.getString("name");

                    JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                    place.latitude = location.getDouble("lat");
                    place.longitude = location.getDouble("lng");

                    for (Object object : result.getJSONArray("address_components")) {
                        JSONObject o = (JSONObject) object;

                        JSONArray types = o.getJSONArray("types");
                        String name = o.getString("long_name");
                        if (isAddressTypeContains("locality", types)) {
                            place.city = name;
                        } else if (isAddressTypeContains("administrative_area_level_1", types)) {
                            place.province = name;
                        } else if (isAddressTypeContains("country", types)) {
                            place.country = name;
                        } else if (isAddressTypeContains("postal_code", types)) {
                            place.postalCode = name;
                        } else if (isAddressTypeContains("sublocality", types)) {
                            place.country = name;
                        }
                    }

                    String address = result.getString("formatted_address");
                    if (null != address) {
                        place.street = address.split(",")[0];
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return place;
    }

    private static boolean isAddressTypeContains(String expected, JSONArray types) {
        for (Object type : types) {
            if (expected.equals(type)) {
                return true;
            }
        }

        return false;
    }
}
