package city.commerce.controller.api;

import city.commerce.controller.api.response.Response;
import city.commerce.entity.VendorEntity;
import city.commerce.model.Permission;
import city.commerce.service.VendorService;
import city.commerce.service.security.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-16
 */
@RestController
@RequestMapping("/api/vendors")
@Permissions({Permission.User})
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Permissions({Permission.Admin})
    public Response<VendorEntity> saveVendor(@RequestBody VendorEntity vendorEntity) {
        return Response.ok(vendorService.saveVendor(vendorEntity));
    }

    @RequestMapping(value = "/{vendorId}", method = RequestMethod.GET)
    public Response<VendorEntity> queryVendor(@PathVariable int vendorId) {
        return Response.ok(vendorService.queryVendor(vendorId));
    }
}
