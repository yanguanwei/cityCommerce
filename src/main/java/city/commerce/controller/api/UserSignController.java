package city.commerce.controller.api;

import city.commerce.controller.api.response.Response;
import city.commerce.entity.UserEntity;
import city.commerce.model.SignInUser;
import city.commerce.model.User;
import city.commerce.model.params.UserSignInParam;
import city.commerce.model.params.UserRegisterParam;
import city.commerce.service.UserService;
import city.commerce.service.security.Permissions;
import city.commerce.service.security.SecurityService;
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
 * @since 2017-04-09
 */
@RestController
@RequestMapping("/api/users")
@Api(tags = {"User"}, description = "Sin up, in and out")
public class UserSignController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ApiOperation(value = "User sign up")
    public Response<SignInUser> signUp(@RequestBody UserRegisterParam userRegisterParam) {
        Validation.validate(userRegisterParam);
        UserEntity userEntity = userService.register(userRegisterParam);
        String token = securityService.generateToken(userEntity.getId());
        SignInUser signInUser = new SignInUser(userEntity, token);
        signInUser.setPassword(null);
        return Response.ok(signInUser);
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ApiOperation(value = "User sign in")
    public Response<SignInUser> signIn(@RequestBody UserSignInParam userSignInParam) {
        SignInUser signInUser = securityService.signIn(userSignInParam);
        signInUser.setPassword(null);
        return Response.ok(signInUser);
    }

    @RequestMapping(value = "/signOut", method = RequestMethod.GET)
    @ApiOperation(value = "User sign out")
    @Permissions
    public Response signOut(@ApiIgnore User user) {
        securityService.signOut(user);
        return Response.ok();
    }
}
