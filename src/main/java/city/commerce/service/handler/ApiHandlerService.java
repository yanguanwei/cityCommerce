package city.commerce.service.handler;

import city.commerce.controller.api.response.Response;
import city.commerce.model.SignInUser;
import city.commerce.model.User;
import city.commerce.model.api.StatusCode;
import city.commerce.model.error.ServiceException;
import city.commerce.service.UserService;
import city.commerce.service.security.Authentication;
import city.commerce.service.security.Permissions;
import city.commerce.service.security.SecurityService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-04
 */
@Service
public class ApiHandlerService extends HandlerInterceptorAdapter implements
        HandlerExceptionResolver {

    private static final String USER_KEY = "__user__";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String appKey = request.getHeader("appKey");
        String sign = request.getHeader("sign");
        String timestamp = request.getHeader("timestamp");

        Authentication authentication = new Authentication(
                appKey,
                sign,
                null == timestamp ? 0 : Integer.valueOf(timestamp)
        );

        String path = request.getPathInfo();
        Map<String, String> params = request.getParameterMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, p -> p.getValue()[0]));

        try {
            securityService.verifySignature(authentication, path, params);

            if (handler instanceof HandlerMethod) {
                HandlerMethod hm = (HandlerMethod) handler;
                Permissions permissions = hm.getMethodAnnotation(Permissions.class);
                if (null == permissions) {
                    permissions = hm.getBean().getClass().getAnnotation(Permissions.class);
                }

                if (null != permissions) {
                    String token = request.getHeader("token");
                    SignInUser signInUser = securityService.verifyUserToken(token);
                    if (null == signInUser) {
                        throw new ServiceException(StatusCode.AuthenticationInvalidToken);
                    }

                    request.setAttribute(UserResolver.USER_KEY, signInUser);
                    if (!signInUser.getPermission().has(permissions.value())) {
                        throw new ServiceException(StatusCode.AuthenticationPermissionDenied);
                    }
                }
            }
        } catch (ServiceException e) {
            handleException(e, response);
            return false;
        }

        return true;
    }

    public void handleException(ServiceException e, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(e.getStatusCode().getStatus());
        response.getWriter().println(JSONObject.toJSON(Response.of(e)));
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof ServiceException) {
                handleException((ServiceException) ex, response);
            } else {
                handleException(new ServiceException(StatusCode.ServerUnknownError), response);
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }
}
