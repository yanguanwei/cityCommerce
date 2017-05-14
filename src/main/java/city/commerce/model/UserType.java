package city.commerce.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
public enum UserType {
    Buyer(0),
    Vendor(1),
    Admin(2)
    ;

    private static Map<Integer, UserType> mapping = new HashMap<>();
    static {
        for (UserType userType : values()) {
            mapping.put(userType.getType(), userType);
        }
    }

    private int type;

    UserType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static UserType of(int type) {
        return mapping.get(type);
    }
}
