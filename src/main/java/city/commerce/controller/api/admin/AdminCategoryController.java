package city.commerce.controller.api.admin;

import city.commerce.controller.api.response.Response;
import city.commerce.entity.CategoryAttributeEntity;
import city.commerce.entity.CategoryEntity;
import city.commerce.model.Permission;
import city.commerce.model.params.CategoryAttributeParam;
import city.commerce.service.CategoryService;
import city.commerce.service.security.Permissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-28
 */
@RestController
@RequestMapping("/api/admin/categories")
@Permissions({Permission.Admin})
@Api(tags = {"Category"}, description = "Category Manager")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/{categoryId}/delete", method = RequestMethod.DELETE)
    @Permissions({Permission.Admin})
    public Response deleteCategory(@PathVariable int categoryId) {
        categoryService.deleteCategory(categoryId);
        return Response.ok();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "Save Category")
    @Permissions({Permission.Admin})
    public Response<CategoryEntity> saveCategory(@RequestBody CategoryEntity categoryEntity) {
        return Response.ok(categoryService.saveCategory(categoryEntity));
    }

    @RequestMapping(value = "/attributes/add", method = RequestMethod.POST)
    @Permissions({Permission.Admin})
    public Response<CategoryAttributeEntity> addCategoryAttribute(@RequestBody CategoryAttributeParam categoryAttributeParam) {
        CategoryAttributeEntity entity = new CategoryAttributeEntity();
        entity.setCategoryId(categoryAttributeParam.getCategoryId());
        entity.setAttributeId(categoryAttributeParam.getAttributeId());
        entity = categoryService.add(entity);
        return Response.ok(entity);
    }

    @RequestMapping(value = "/attributes/delete", method = RequestMethod.DELETE)
    @Permissions({Permission.Admin})
    public Response deleteCategoryAttribute(@RequestBody CategoryAttributeParam categoryAttributeParam) {
        CategoryAttributeEntity entity = new CategoryAttributeEntity();
        entity.setCategoryId(categoryAttributeParam.getCategoryId());
        entity.setCategoryId(categoryAttributeParam.getAttributeId());
        categoryService.deleteCategoryAttribute(entity);
        return Response.ok();
    }
}
