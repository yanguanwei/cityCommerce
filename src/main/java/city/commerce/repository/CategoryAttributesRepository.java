package city.commerce.repository;

import city.commerce.entity.CategoryAttributeEntity;
import city.commerce.repository.mapper.CategoryAttributeMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-08
 */
@Repository
public class CategoryAttributesRepository {

    @Autowired
    private CategoryAttributeMapper categoryAttributeMapper;

    public CategoryAttributeEntity add(CategoryAttributeEntity categoryAttributeEntity) {
        categoryAttributeMapper.add(categoryAttributeEntity);
        return categoryAttributeEntity;
    }

    public CategoryAttributeEntity queryCategoryAttribute(int categoryId, int attributeId) {
        return categoryAttributeMapper.findByCategoryIdAndAttributeId(categoryId, attributeId);
    }

    public int[] queryCategoryAttributes(int categoryId) {
        List<CategoryAttributeEntity> entities = categoryAttributeMapper.findAllByCategoryId(categoryId);
        if (CollectionUtils.isNotEmpty(entities)) {
            return entities.stream().mapToInt(CategoryAttributeEntity::getAttributeId).toArray();
        }

        return new int[0];
    }

    public void deleteByCategoryId(int categoryId) {
        categoryAttributeMapper.deleteByCategoryId(categoryId);
    }

    public void deleteByAttributeId(int attributeId) {
        categoryAttributeMapper.deleteByAttributeId(attributeId);
    }

    public void delete(int categoryId, int attributeId) {
        categoryAttributeMapper.deleteByCategoryAndAttribute(categoryId, attributeId);
    }
}
