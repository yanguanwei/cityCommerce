package city.commerce.service.handler;

import city.commerce.model.SignInUser;
import city.commerce.model.User;
import city.commerce.model.api.StatusCode;
import city.commerce.model.error.ServiceException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-10
 */
public class UserResolver implements HandlerMethodArgumentResolver {
    public static final String USER_KEY = "__user__";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return User.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        User signInUser = (User) webRequest.getAttribute(USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (null == signInUser) {
            throw new ServiceException(StatusCode.AuthenticationSignInRequired);
        }

        return signInUser;
    }
}
