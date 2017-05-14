package city.commerce.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
public enum DivisionLevel {
    Country(0),
    Province(1),
    City(2),
    County(3)
    ;

    private int level;

    private static final Map<Integer, DivisionLevel> mapping = new HashMap<>();
    static {
        for (DivisionLevel divisionLevel : values()) {
            mapping.put(divisionLevel.getLevel(), divisionLevel);
        }
    }

    DivisionLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static DivisionLevel of(int level) {
        return mapping.get(level);
    }
}
