package city.commerce.repository;

import city.commerce.entity.AttributeEntity;
import city.commerce.entity.AttributeValueEntity;
import city.commerce.repository.mapper.AttributeMapper;
import city.commerce.repository.mapper.AttributeValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-07
 */
@Repository
public class AttributeRepository {
    @Autowired
    private AttributeMapper attributeMapper;

    @Autowired
    private AttributeValueMapper attributeValueMapper;

    @Autowired
    private CategoryAttributesRepository categoryAttributesRepository;


    public List<AttributeEntity> queryAllAttributes() {
        return attributeMapper.all();
    }

    public List<AttributeValueEntity> queryAllAttributeValue() {
        return attributeValueMapper.all();
    }

    public List<AttributeEntity> queryAttributes() {
        return attributeMapper.all();
    }

    public AttributeEntity addAttribute(AttributeEntity attributeEntity) {
        attributeMapper.add(attributeEntity);
        return attributeEntity;
    }

    public AttributeEntity updateAttribute(AttributeEntity attributeEntity) {
        attributeMapper.update(attributeEntity);
        return attributeEntity;
    }

    public AttributeValueEntity addAttributeValue(AttributeValueEntity attributeValueEntity) {
        attributeValueMapper.add(attributeValueEntity);
        return attributeValueEntity;
    }

    public AttributeValueEntity updateAttributeValue(AttributeValueEntity attributeValueEntity) {
        attributeValueMapper.update(attributeValueEntity);
        return attributeValueEntity;
    }

    public void deleteAttributeValue(int attributeValueId) {
        attributeValueMapper.delete(attributeValueId);
    }

    @Transactional
    public void deleteAttribute(int attributeId) {
        attributeValueMapper.deleteByAttributeId(attributeId);
        categoryAttributesRepository.deleteByAttributeId(attributeId);
        attributeMapper.delete(attributeId);
    }
}
