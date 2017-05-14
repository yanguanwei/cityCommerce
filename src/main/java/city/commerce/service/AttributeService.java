package city.commerce.service;

import city.commerce.entity.AttributeEntity;
import city.commerce.entity.AttributeValueEntity;
import city.commerce.model.Attribute;
import city.commerce.model.api.StatusCode;
import city.commerce.model.error.ServiceException;
import city.commerce.repository.AttributeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-07
 */
@Service
@Slf4j
public class AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    private Map<Integer, AttributeEntity> attributeMap = new ConcurrentHashMap<>();
    private Map<Integer, AttributeValueEntity> attributeValueMap = new ConcurrentHashMap<>();
    private Map<Integer, List<AttributeValueEntity>> attributeValueByAttributeMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        log.info("caching attributes and values ...");

        List<AttributeEntity> attributeEntities = attributeRepository.queryAllAttributes();
        if (CollectionUtils.isNotEmpty(attributeEntities)) {
            for (AttributeEntity attributeEntity : attributeEntities) {
                attributeMap.put(attributeEntity.getId(), attributeEntity);
            }
        }

        log.info("cached attributes size : {}", null == attributeEntities ? 0 : attributeEntities.size());

        List<AttributeValueEntity> attributeValueEntities = attributeRepository.queryAllAttributeValue();
        if (CollectionUtils.isNotEmpty(attributeValueEntities)) {
            for (AttributeValueEntity attributeValueEntity : attributeValueEntities) {
                attributeValueMap.put(attributeValueEntity.getId(), attributeValueEntity);
                getAttributeValueList(attributeValueEntity.getAttributeId()).add(attributeValueEntity);
            }
        }

        log.info("cached attribute's values size: {}", null == attributeValueEntities ? 0 : attributeValueEntities.size());
    }

    private List<AttributeValueEntity> getAttributeValueList(int attributeId) {
        List<AttributeValueEntity> list = attributeValueByAttributeMap.get(attributeId);
        if (null == list) {
            list = new ArrayList<>();
            List<AttributeValueEntity> prev = attributeValueByAttributeMap.putIfAbsent(attributeId, list);
            if (null != prev) {
                list = prev;
            }
        }
        return list;
    }

    public AttributeEntity queryAttributeEntity(int attributeId) {
        return attributeMap.get(attributeId);
    }

    public Attribute queryAttribute(int attributeId) {
        AttributeEntity attributeEntity = queryAttributeEntity(attributeId);
        if (null != attributeEntity) {
            return convert(attributeEntity);
        }
        return null;
    }

    public List<AttributeEntity> queryAttributeEntities(int... attributeIds) {
        List<AttributeEntity> list = new ArrayList<>();
        for (int attributeId : attributeIds) {
            AttributeEntity attributeEntity = attributeMap.get(attributeId);
            if (null != attributeEntity) {
                list.add(attributeEntity);
            }
        }
        return list;
    }

    public List<Attribute> convert(List<AttributeEntity> attributeEntities) {
        List<Attribute> attributes = new ArrayList<>();
        for (AttributeEntity attributeEntity : attributeEntities) {
            attributes.add(convert(attributeEntity));
        }
        return attributes;
    }

    public Attribute convert(AttributeEntity attributeEntity) {
        Attribute attribute = new Attribute(attributeEntity);
        List<AttributeValueEntity> attributeValueEntities = queryAttributeValues(attributeEntity.getId());
        if (CollectionUtils.isNotEmpty(attributeValueEntities)) {
            for (AttributeValueEntity attributeValueEntity : attributeValueEntities) {
                attribute.addValue(attributeValueEntity);
            }
        }
        return attribute;
    }

    public List<AttributeValueEntity> queryAttributeValues(int attributeId) {
        List<AttributeValueEntity> list = attributeValueByAttributeMap.get(attributeId);
        if (null != list) {
            return Collections.unmodifiableList(list);
        }
        return Collections.emptyList();
    }

    public List<AttributeEntity> queryAttributeEntities() {
        return attributeRepository.queryAttributes();
    }

    public AttributeEntity saveAttribute(AttributeEntity attributeEntity) {
        if (attributeEntity.getId() > 0) {
            AttributeEntity exist = attributeMap.get(attributeEntity.getId());
            if (null != exist) {
                attributeEntity = attributeRepository.updateAttribute(attributeEntity);
                exist.setName(attributeEntity.getName());
                exist.setDescription(attributeEntity.getDescription());
            } else {
                throw new ServiceException(StatusCode.ResourceNotFound);
            }
        } else {
            attributeEntity = attributeRepository.addAttribute(attributeEntity);
            attributeMap.put(attributeEntity.getId(), attributeEntity);
        }

        return attributeEntity;
    }

    public AttributeValueEntity saveAttributeValue(AttributeValueEntity attributeValueEntity) {
        if (attributeValueEntity.getId() > 0) {
            AttributeValueEntity exist = attributeValueMap.get(attributeValueEntity.getId());
            if (null != exist) {
                attributeRepository.updateAttributeValue(attributeValueEntity);
                exist.setValue(attributeValueEntity.getValue());
            } else {
                throw new ServiceException(StatusCode.ResourceNotFound);
            }
        } else {
            attributeRepository.addAttributeValue(attributeValueEntity);
            attributeValueMap.put(attributeValueEntity.getId(), attributeValueEntity);
            getAttributeValueList(attributeValueEntity.getAttributeId()).add(attributeValueEntity);
        }

        return attributeValueEntity;
    }

    public void deleteAttributeValue(int attributeValueId) {
        attributeRepository.deleteAttributeValue(attributeValueId);
        AttributeValueEntity attributeValueEntity = attributeValueMap.remove(attributeValueId);
        if (null != attributeValueEntity) {
            getAttributeValueList(attributeValueEntity.getAttributeId()).remove(attributeValueEntity);
        }
    }

    public void deleteAttribute(int attributeId) {
        attributeRepository.deleteAttribute(attributeId);
        AttributeEntity attributeEntity = attributeMap.remove(attributeId);
        if (null != attributeEntity) {
            List<AttributeValueEntity> attributeValueEntities = attributeValueByAttributeMap.remove(attributeEntity.getId());
            if (CollectionUtils.isNotEmpty(attributeValueEntities)) {
                for (AttributeValueEntity attributeValueEntity : attributeValueEntities) {
                    attributeValueMap.remove(attributeValueEntity.getId());
                }
            }
        }
    }
}
