package city.commerce.service;

import city.commerce.entity.CategoryEntity;
import city.commerce.entity.DivisionEntity;
import city.commerce.model.CategoryTree;
import city.commerce.model.Division;
import city.commerce.model.DivisionTree;
import city.commerce.model.api.StatusCode;
import city.commerce.model.error.ErrorCode;
import city.commerce.model.error.ServiceException;
import city.commerce.repository.DivisionRepository;
import city.commerce.repository.mapper.DivisionMapper;
import city.commerce.validation.Validation;
import city.commerce.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-16
 */
@Slf4j
@Service
public class DivisionService {
    @Autowired
    private DivisionRepository divisionRepository;

    private Map<Integer, DivisionEntity> divisionMap = new ConcurrentHashMap<>();
    private Map<Integer, Set<Integer>> divisionByParentMap = new ConcurrentHashMap<>();
    private Map<String, DivisionEntity> divisionByNameMap = new ConcurrentHashMap<>();


    private class DivisionTreeBuilder {
        private Set<Integer> visited = new HashSet<>();

        List<DivisionTree> build(int parentId) {
            Set<Integer> sub = divisionByParentMap.get(parentId);
            List<DivisionTree> trees = new ArrayList<>();

            if (null != sub) {
                for (int id : sub) {
                    DivisionEntity entity = divisionMap.get(id);
                    DivisionTree tree = new DivisionTree(entity);
                    trees.add(tree);
                    build(tree);
                }
            }

            return trees;
        }


        private void build(DivisionTree tree) {
            if (visited.contains(tree.getId())) {
                throw new ServiceException(StatusCode.DivisionTreeCircled);
            }
            visited.add(tree.getId());

            Set<Integer> sub = divisionByParentMap.get(tree.getId());
            if (null != sub) {
                for (int id : sub) {
                    DivisionEntity entity = divisionMap.get(id);
                    DivisionTree subTree = new DivisionTree(entity);
                    tree.addChild(subTree);
                    build(subTree);
                }
            }
        }
    }

    @PostConstruct
    public void init() {
        log.info("caching divisions ...");

        List<DivisionEntity> divisionEntities = divisionRepository.queryAllDivisions();
        if (CollectionUtils.isNotEmpty(divisionEntities)) {
            for (DivisionEntity divisionEntity : divisionEntities) {
                cacheDivision(divisionEntity);
            }
        }

        log.info("cached division size: {}", divisionEntities == null ? 0 : divisionEntities.size());
    }

    private void cacheDivision(DivisionEntity divisionEntity) {
        divisionMap.put(divisionEntity.getId(), divisionEntity);
        Set<Integer> subDivisions = divisionByParentMap.computeIfAbsent(divisionEntity.getParentId(), p -> new HashSet<>());
        subDivisions.add(divisionEntity.getId());
        divisionByNameMap.put(divisionEntity.getName(), divisionEntity);
    }

    public List<DivisionTree> queryDivisionTrees(int parentId) {
        return new DivisionTreeBuilder().build(parentId);
    }

    public DivisionEntity queryDivision(int id) {
        DivisionEntity divisionEntity = divisionMap.get(id);
        if (null == divisionEntity) {
            divisionEntity = divisionRepository.queryDivision(id);
            if (null != divisionEntity) {
                divisionMap.putIfAbsent(divisionEntity.getId(), divisionEntity);
                divisionByNameMap.putIfAbsent(divisionEntity.getName(), divisionEntity);
            }
        }

        return divisionEntity;
    }

    public DivisionEntity queryDivisionByName(String name) {
        DivisionEntity divisionEntity = divisionByNameMap.get(name);
        if (null == divisionEntity) {
            divisionEntity = divisionRepository.queryDivisionByName(name);
            if (null != divisionEntity) {
                cacheDivision(divisionEntity);
            }
        }

        return divisionEntity;
    }

    public List<DivisionEntity> queryDivisionParentPath(int id) {
        List<DivisionEntity> path = new ArrayList<>();
        DivisionEntity current = queryDivision(id);
        while (current != null) {
            path.add(current);
            current = current.getParentId() > 0 ? queryDivision(current.getParentId()) : null;
        }

        if (!path.isEmpty()) {
            Collections.reverse(path);
        }

        return path;
    }

    public DivisionEntity saveDivision(Division division) {
        Validation.validate(division);

        if (division.getParentId() > 0) {
            if (null == queryDivision(division.getParentId())) {
                throw new ValidationException("parentId", ErrorCode.FIELD_INVALID);
            }
        }

        DivisionEntity divisionEntity = division.toEntity();
        if (divisionEntity.getId() > 0) {
            divisionEntity = divisionRepository.updateDivision(divisionEntity);
        } else {
            if (null != queryDivisionByName(divisionEntity.getName())) {
                throw new ValidationException("name", ErrorCode.FIELD_EXISTED);
            }
            divisionEntity = divisionRepository.addDivision(divisionEntity);
        }

        cacheDivision(divisionEntity);

        return divisionEntity;
    }

    public void deleteDivision(int divisionId) {
        DivisionEntity divisionEntity = divisionMap.remove(divisionId);
        if (null == divisionEntity) {
            throw new ServiceException(StatusCode.ResourceNotFound);
        }
        divisionByNameMap.remove(divisionEntity.getName());
        divisionByParentMap.get(divisionEntity.getParentId()).remove(divisionId);
        divisionRepository.deleteDivision(divisionId);
    }
}
