package city.commerce.controller.api;

import city.commerce.controller.api.response.Response;
import city.commerce.model.DivisionTree;
import city.commerce.service.DivisionService;
import city.commerce.service.security.Permissions;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-28
 */
@RestController
@RequestMapping("/api/divisions")
@Api(tags = {"Division"}, description = "Division Query")
@Permissions
public class DivisionController {
    @Autowired
    private DivisionService divisionService;

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Response<List<DivisionTree>> queryDivisionTrees() {
        return Response.ok(divisionService.queryDivisionTrees(0));
    }

    @RequestMapping(value = "/{divisionId}/tree", method = RequestMethod.GET)
    public Response<List<DivisionTree>> queryDivisionTrees(@PathVariable int divisionId) {
        return Response.ok(divisionService.queryDivisionTrees(divisionId));
    }
}
