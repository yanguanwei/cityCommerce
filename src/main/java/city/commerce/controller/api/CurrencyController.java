package city.commerce.controller.api;

import city.commerce.controller.api.response.Response;
import city.commerce.model.Currency;
import city.commerce.service.security.Permissions;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
@RestController
@RequestMapping("/api/currencies")
@Api(tags = {"Currency"}, description = "Supported Currency")
@Permissions
public class CurrencyController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<Currency>> queryCurrencies() {
        return Response.ok(Arrays.asList(Currency.values()));
    }
}
