package city.commerce.service;

import city.commerce.entity.ProductEntity;
import city.commerce.entity.ProductSkuEntity;
import city.commerce.model.Attribute;
import city.commerce.model.ProductAttribute;
import city.commerce.model.ProductSku;
import city.commerce.model.error.ErrorCode;
import city.commerce.model.params.AddProductSkuParam;
import city.commerce.model.params.ProductAttributeValueParam;
import city.commerce.repository.ProductSkuRepository;
import city.commerce.validation.Validation;
import city.commerce.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author young.alway@gmail.com
 * @since 2017-05-03
 */
@Service
public class ProductSkuService {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Autowired
    private AttributeService attributeService;

    public ProductSkuEntity addSku(AddProductSkuParam addProductSkuParam) {
        Validation.validate(addProductSkuParam);

        ProductEntity productEntity = productService.queryProduct(addProductSkuParam.getProductId());
        if (null == productEntity) {
            throw new ValidationException("productId", ErrorCode.FIELD_INVALID);
        }

        List<ProductAttribute> productAttributes = productService.queryAllProductAttributes(addProductSkuParam.getProductId());
        Map<Integer, ProductAttribute> productAttributeMap = productAttributes.stream().collect(Collectors.toMap(p->p.getAttributeId(), p->p));
        List<ProductAttributeValueParam> attributeValueParams = addProductSkuParam.getAttributes();
        for (ProductAttributeValueParam attributeValueParam : attributeValueParams) {
            ProductAttribute attribute = productAttributeMap.get(attributeValueParam.getAttributeId());
            if (null == attribute || null == attribute.getValues()) {
                throw new ValidationException("attribute:"+attributeValueParam.getAttributeId(), ErrorCode.FIELD_INVALID);
            }

            ProductAttribute.Value value = attribute.getValue(attributeValueParam.getValueId());
            if (null == value) {
                throw new ValidationException("attribute:"+attribute.getAttributeId()+";value:"+attributeValueParam.getValueId(), ErrorCode.FIELD_INVALID);
            }
        }


        return null;
    }
}
