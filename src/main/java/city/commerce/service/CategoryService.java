package city.commerce.service;

import city.commerce.entity.CategoryAttributeEntity;
import city.commerce.entity.CategoryEntity;
import city.commerce.model.Attribute;
import city.commerce.model.Category;
import city.commerce.model.CategoryTree;
import city.commerce.model.api.StatusCode;
import city.commerce.model.error.ErrorCode;
import city.commerce.model.error.ServiceException;
import city.commerce.repository.CategoryAttributesRepository;
import city.commerce.repository.CategoryRepository;
import city.commerce.validation.ValidationException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-08
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private CategoryAttributesRepository categoryAttributesRepository;

    private Map<Integer, CategoryEntity> categoryMap = new ConcurrentHashMap<>();
    private Map<Integer, Set<CategoryEntity>> subCategoryMap = new ConcurrentHashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    @PostConstruct
    public void init() {
        LOGGER.info("caching categories ...");
        List<CategoryEntity> categories = categoryRepository.queryAllCategories();
        if (CollectionUtils.isNotEmpty(categories)) {
            for (CategoryEntity categoryEntity : categories) {
                categoryMap.put(categoryEntity.getId(), categoryEntity);
                getSubCategories(categoryEntity.getParentId()).add(categoryEntity);
            }
        }
        LOGGER.info("cached categories size: {}", categories == null ? 0 : categories.size());
    }

    private class CategoryTreeBuilder {
        private Set<Integer> visited = new HashSet<>();

        List<CategoryTree> build(int parentId) {
            Set<CategoryEntity> sub = subCategoryMap.get(parentId);
            List<CategoryTree> trees = new ArrayList<>();

            if (null != sub) {
                for (CategoryEntity categoryEntity : sub) {
                    CategoryTree tree = new CategoryTree(categoryEntity);
                    trees.add(tree);
                    build(tree);
                }
            }

            return trees;
        }


        private void build(CategoryTree tree) {
            if (visited.contains(tree.getId())) {
                throw new ServiceException(StatusCode.CategoryTreeCircled);
            }
            visited.add(tree.getId());

            Set<CategoryEntity> sub = subCategoryMap.get(tree.getId());
            if (null != sub) {
                for (CategoryEntity categoryEntity : sub) {
                    CategoryTree subTree = new CategoryTree(categoryEntity);
                    tree.addChild(subTree);
                    build(subTree);
                }
            }
        }
    }

    public List<CategoryTree> getCategoryTrees(int parentId) {
        return new CategoryTreeBuilder().build(parentId);
    }

    public CategoryAttributeEntity add(CategoryAttributeEntity entity) {
        if (getCategory(entity.getCategoryId()) == null) {
            throw new ValidationException("categoryId", ErrorCode.FIELD_INVALID);
        }

        if (attributeService.queryAttribute(entity.getAttributeId()) == null) {
            throw new ValidationException("attributeId", ErrorCode.FIELD_INVALID);
        }

        if (categoryAttributesRepository.queryCategoryAttribute(entity.getCategoryId(), entity.getAttributeId()) != null) {
            throw new ServiceException(StatusCode.ResourceExisted);
        }

        return categoryAttributesRepository.add(entity);
    }

    public List<Attribute> queryCategoryAttributes(int categoryId) {
        int[] attributeIds = queryCategoryAttributeIds(categoryId);
        return attributeService.convert(attributeService.queryAttributeEntities(attributeIds));
    }

    public int[] queryCategoryAttributeIds(int categoryId) {
        return categoryRepository.queryCategoryAttributes(categoryId);
    }

    public CategoryEntity getCategory(int id) {
        return categoryMap.get(id);
    }

    public List<Category> getParentCategories(int id) {
        List<Category> categories = new ArrayList<>();
        CategoryEntity category = categoryMap.get(id);
        while (null != category) {
            categories.add(new Category(category));
            category = categoryMap.get(category.getParentId());
        }
        return categories;
    }

    public List<CategoryEntity> queryRootCategories() {
        return querySubCategories(0);
    }

    public List<CategoryEntity> querySubCategories(int parentId) {
        Set<CategoryEntity> list = subCategoryMap.get(parentId);
        return null == list ? Collections.EMPTY_LIST : new ArrayList<>(list);
    }

    public CategoryEntity saveCategory(CategoryEntity categoryEntity) {
        if (categoryEntity.getId() > 0) {
            CategoryEntity exist = categoryMap.get(categoryEntity.getId());
            if (null == exist) {
                throw new ServiceException(StatusCode.ResourceNotFound);
            }
            categoryRepository.updateCategory(categoryEntity);
            exist.setName(categoryEntity.getName());
            exist.setDescription(categoryEntity.getDescription());
            return exist;
        } else {
            categoryRepository.addCategory(categoryEntity);
            categoryMap.put(categoryEntity.getId(), categoryEntity);
            getSubCategories(categoryEntity.getParentId()).add(categoryEntity);
        }

        return categoryEntity;
    }

    private Set<CategoryEntity> getSubCategories(int parentId) {
        Set<CategoryEntity> set = subCategoryMap.get(parentId);
        if (null == set) {
            set = new TreeSet<>();
            Set<CategoryEntity> prev = subCategoryMap.putIfAbsent(parentId, set);
            if (null != prev) {
                set = prev;
            }
        }
        return set;
    }

    public void updateCategoryParent(int id, int parentId) {
        CategoryEntity categoryEntity = categoryMap.get(id);
        if (null == categoryEntity) {
            throw new ServiceException(StatusCode.ResourceNotFound);
        }

        categoryRepository.updateCategoryParent(id, parentId);
        subCategoryMap.get(categoryEntity.getParentId()).remove(categoryEntity);
        getSubCategories(parentId).add(categoryEntity);
    }

    public void updateCategorySequence(int id, int sequence) {
        CategoryEntity categoryEntity = categoryMap.get(id);
        if (null == categoryEntity) {
            throw new ServiceException(StatusCode.ResourceNotFound);
        }

        categoryRepository.updateCategorySequence(id, sequence);
        categoryEntity.setSequence(sequence);
    }

    public void deleteCategory(int id) {
        CategoryEntity categoryEntity = categoryMap.get(id);
        if (null == categoryEntity) {
            throw new ServiceException(StatusCode.ResourceNotFound);
        }

        categoryRepository.delete(id);
        categoryRepository.updateCategoryParentByParentId(id, categoryEntity.getParentId());

        categoryMap.remove(id);
        getSubCategories(categoryEntity.getParentId()).remove(categoryEntity);

        Set<CategoryEntity> sub = subCategoryMap.remove(id);
        if (null != sub) {
            getSubCategories(categoryEntity.getParentId()).addAll(sub);
        }
    }

    public void deleteCategoryAttribute(CategoryAttributeEntity entity) {
        categoryAttributesRepository.delete(entity.getCategoryId(), entity.getAttributeId());
    }
}
