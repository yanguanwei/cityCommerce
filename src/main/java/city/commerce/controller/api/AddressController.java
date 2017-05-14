package city.commerce.controller.api;

import city.commerce.controller.api.response.ApiResponse;
import city.commerce.entity.Address;
import city.commerce.repository.AddressRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@RestController
@Api("Address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping(value = "/api/address/{id}", method = RequestMethod.GET)
    public ApiResponse<Address> getAddress(@PathVariable("id") int id) {
        return ApiResponse.successOf(addressRepository.queryAddress(id));
    }
}
