package city.commerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author young.alway@gmail.com
 * @since 2017-05-14
 */
@Controller
public class ApiDocController {
    @RequestMapping(value = "/api-doc.htm", method = RequestMethod.GET)
    public String doc() {
        return "redirect:swagger-ui.htm";
    }
}
