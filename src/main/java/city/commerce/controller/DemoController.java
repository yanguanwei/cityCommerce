package city.commerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-18
 */
@Controller
public class DemoController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "location";
    }
}
