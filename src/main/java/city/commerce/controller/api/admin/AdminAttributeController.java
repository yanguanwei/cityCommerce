package city.commerce.controller.api.admin;

import city.commerce.controller.api.response.Response;
import city.commerce.entity.AttributeEntity;
import city.commerce.entity.AttributeValueEntity;
import city.commerce.model.Permission;
import city.commerce.service.AttributeService;
import city.commerce.service.security.Permissions;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-28
 */
@RestController
@RequestMapping("/api/admin/attributes")
@Permissions({Permission.Admin})
@Api(tags = {"Attribute"}, description = "Attribute Manager")
public class AdminAttributeController {
    @Autowired
    private AttributeService attributeService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response<AttributeEntity> saveAttribute(@RequestBody AttributeEntity attributeEntity) {
        attributeEntity = attributeService.saveAttribute(attributeEntity);
        return Response.ok(attributeEntity);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<AttributeEntity>> queryAttributes() {
        return Response.ok(attributeService.queryAttributeEntities());
    }

    @RequestMapping(value = "/{attributeId}/values", method = RequestMethod.GET)
    public Response<List<AttributeValueEntity>> queryAttributeValues(@PathVariable int attributeId) {
        return Response.ok(attributeService.queryAttributeValues(attributeId));
    }

    @RequestMapping(value = "/values/save", method = RequestMethod.POST)
    public Response<AttributeValueEntity> saveValue(@RequestBody AttributeValueEntity attributeValueEntity) {
        attributeValueEntity = attributeService.saveAttributeValue(attributeValueEntity);
        return Response.ok(attributeValueEntity);
    }

    @RequestMapping(value = "/{attributeId}/delete", method = RequestMethod.DELETE)
    public Response deleteAttribute(@PathVariable int attributeId) {
        attributeService.deleteAttribute(attributeId);
        return Response.ok();
    }

    @RequestMapping(value = "/values/{attributeValueId}/delete", method = RequestMethod.DELETE)
    public Response deleteAttributeValue(@PathVariable int attributeValueId) {
        attributeService.deleteAttributeValue(attributeValueId);
        return Response.ok();
    }
}
