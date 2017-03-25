package city.commerce.entity;

import lombok.Data;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-22
 */
@Data
public class Category {
    private int id;
    private String name;
    private String description;
    private int parentId;
    private Category parent;
    private List<Category> children;
    private int rootId;
}
