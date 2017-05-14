package city.commerce.controller.api.admin;

import city.commerce.controller.api.response.Response;
import city.commerce.model.Division;
import city.commerce.model.Permission;
import city.commerce.service.DivisionService;
import city.commerce.service.security.Permissions;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@RestController
@RequestMapping("/api/admin/divisions")
@Permissions({Permission.Admin})
@Api(tags = "Division", description = "Division Manager")
public class AdminDivisionController {

    @Autowired
    private DivisionService divisionService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response<Division> saveDivision(@RequestBody Division division) {
        divisionService.saveDivision(division);
        return Response.ok(division);
    }

    @RequestMapping(value = "/{divisionId}/delete", method = RequestMethod.DELETE)
    public Response deleteDivision(@PathVariable int divisionId) {
        divisionService.deleteDivision(divisionId);
        return Response.ok();
    }
}
