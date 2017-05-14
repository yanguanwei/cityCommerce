package city.commerce.model;

import city.commerce.entity.DivisionEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-28
 */
public class DivisionTree extends DivisionEntity {
    private List<DivisionTree> children;

    public DivisionTree(DivisionEntity entity) {
        this.setId(entity.getId());
        this.setName(entity.getName());
        this.setParentId(entity.getParentId());
        this.setLevel(entity.getLevel());
    }

    public void addChild(DivisionTree divisionTree) {
        if (null == children) {
            children = new ArrayList<>();
        }
        children.add(divisionTree);
    }

    public List<DivisionTree> getChildren() {
        return children;
    }
}
