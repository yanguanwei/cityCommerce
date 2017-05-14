package city.commerce.service.search;

import city.commerce.entity.Location;
import lombok.Data;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-26
 */
@Data
public class SearchCondition {
    private String keyword;

    private int categoryId;
    private int divisionId;
    private Location location;

    private int storeSize = 10;
    private int productSize = 10;
}
