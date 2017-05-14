package city.commerce.controller.api;

import city.commerce.controller.api.response.Response;
import city.commerce.entity.UserEntity;
import city.commerce.model.*;
import city.commerce.service.UserService;
import city.commerce.service.security.*;
import city.commerce.validation.Validation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-01
 */
@RestController
@Permissions({Permission.User})
@RequestMapping("/api/users")
@Api(tags = {"User"}, description = "User Manager")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ApiOperation(value = "Get user profile")
    public Response<UserProfile> userProfile(@ApiIgnore User user) {
        return Response.ok(userService.queryUserProfile(user.getUserId()));
    }

    @RequestMapping(value = "/profile/save", method = RequestMethod.POST)
    @ApiOperation(value = "Save user profile")
    public Response<UserProfile> saveUserProfile(@ApiIgnore User user, @RequestBody UserProfile userProfile) {
        userProfile = userService.saveUserProfile(user.getUserId(), userProfile);
        return Response.ok(userProfile);
    }
}
