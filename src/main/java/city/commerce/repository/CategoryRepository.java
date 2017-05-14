package city.commerce.repository;

import city.commerce.entity.CategoryEntity;
import city.commerce.repository.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@Repository
public class CategoryRepository {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryAttributesRepository categoryAttributesRepository;

    public List<CategoryEntity> queryAllCategories() {
        return categoryMapper.all();
    }

    public CategoryEntity addCategory(CategoryEntity categoryEntity) {
        categoryMapper.add(categoryEntity);
        return categoryEntity;
    }

    public CategoryEntity updateCategory(CategoryEntity categoryEntity) {
        categoryMapper.update(categoryEntity);
        return categoryEntity;
    }

    public void updateCategoryParent(int id, int parentId) {
        categoryMapper.updateParent(id, parentId);
    }

    public void updateCategoryParentByParentId(int originParentId, int parentId) {
        categoryMapper.updateParentByParentId(originParentId, parentId);
    }

    public void updateCategorySequence(int id, int sequence) {
        categoryMapper.updateSequence(id, sequence);
    }

    public int[] queryCategoryAttributes(int categoryId) {
        return categoryAttributesRepository.queryCategoryAttributes(categoryId);
    }

    public void delete(int id) {
        categoryMapper.delete(id);
        categoryAttributesRepository.deleteByCategoryId(id);
    }
}
