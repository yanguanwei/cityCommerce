package city.commerce.controller.api;

import city.commerce.controller.api.response.Response;
import city.commerce.entity.CategoryAttributeEntity;
import city.commerce.model.Attribute;
import city.commerce.model.CategoryTree;
import city.commerce.entity.CategoryEntity;
import city.commerce.model.Permission;
import city.commerce.model.params.CategoryAttributeParam;
import city.commerce.service.CategoryService;
import city.commerce.service.security.Permissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@RestController
@RequestMapping("/api/categories")
@Api(tags = {"Category"}, description = "Category Query")
@Permissions
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/root", method = RequestMethod.GET)
    public Response<List<CategoryEntity>> getRootCategory() {
        return Response.ok(categoryService.queryRootCategories());
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Response<List<CategoryTree>> getCategoryTrees() {
        return Response.ok(categoryService.getCategoryTrees(0));
    }

    @RequestMapping(value = "/{parentId}/sub", method = RequestMethod.GET)
    public Response<List<CategoryEntity>> getSubCategories(@PathVariable int parentId) {
        return Response.ok(categoryService.querySubCategories(parentId));
    }

    @RequestMapping(value = "/{categoryId}/attributes", method = RequestMethod.GET)
    public Response<List<Attribute>> getCategoryAttributes(@PathVariable int categoryId) {
        List<Attribute> attributes = categoryService.queryCategoryAttributes(categoryId);
        return Response.ok(attributes);
    }
}
