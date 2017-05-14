package city.commerce.model;

import city.commerce.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-08
 */
public class CategoryTree extends CategoryEntity {
    private List<CategoryTree> children;

    public CategoryTree(CategoryEntity categoryEntity) {
        this.setId(categoryEntity.getId());
        this.setName(categoryEntity.getName());
        this.setDescription(categoryEntity.getDescription());
        this.setParentId(categoryEntity.getParentId());
        this.setSequence(categoryEntity.getSequence());
    }

    public void addChild(CategoryTree categoryTree) {
        if (null == children) {
            children = new ArrayList<>();
        }
        children.add(categoryTree);
    }

    public List<CategoryTree> getChildren() {
        return children;
    }
}
