package city.commerce.controller.api;

import city.commerce.controller.api.response.Response;
import city.commerce.model.Category;
import city.commerce.model.Product;
import city.commerce.model.ProductAttribute;
import city.commerce.model.params.ProductAttributeValueParam;
import city.commerce.model.params.ProductParam;
import city.commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-07
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public Response<Product> queryProduct(@PathVariable int productId) {
        return Response.ok(productService.convert(productService.queryProduct(productId)));
    }

    @RequestMapping(value = "/{productId}/attributes", method = RequestMethod.GET)
    public Response<List<ProductAttribute>> queryProductAttributes(@PathVariable int productId) {
        return Response.ok(productService.queryAllProductAttributes(productId));
    }

    @RequestMapping(value = "/{productId}/attributes/save", method = RequestMethod.GET)
    public Response<List<ProductAttribute>> saveProductAttributes(@PathVariable int productId, @RequestBody List<ProductAttributeValueParam> productAttributeValueParams) {
        return Response.ok(productService.saveProductAttributes(productId, productAttributeValueParams));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response<Product> saveProduct(@RequestBody ProductParam productParam) {
        return Response.ok(productService.convert(productService.save(productParam)));
    }
}
