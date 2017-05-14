package city.commerce.init;

import city.commerce.service.handler.ApiHandlerService;
import city.commerce.service.handler.UserResolver;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-01
 */
@Configuration
@MapperScan({"city.commerce.repository.mapper"})
@ComponentScan(basePackages = {
        "city.commerce.service",
        "city.commerce.controller",
        "city.commerce.repository",
        "city.commerce.service"
})
public class ApiConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private ApiHandlerService apiHandlerService;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new FastJsonHttpMessageConverter());
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(apiHandlerService);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiHandlerService)
            .addPathPatterns("/api/**");
    }
}
