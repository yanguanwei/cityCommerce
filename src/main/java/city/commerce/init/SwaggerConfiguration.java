package city.commerce.init;

import city.commerce.controller.api.response.Response;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-26
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Autowired
    private TypeResolver typeResolver;

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("City Commerce Admin Manager")
                .description("City Commerce Admin API")
                .termsOfServiceUrl("http://github.com/yanguanwei/cityCommerce")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalOperationParameters(globalOperationParameters())
//                .globalResponseMessage(
//                        RequestMethod.GET,
//                        Lists.newArrayList(
//                                new ResponseMessageBuilder()
//                                        .code(500)
//                                        .message("500 message")
//                                        .responseModel(new ModelRef("Error"))
//                                        .build()
//                        ))
                .genericModelSubstitutes(Response.class)
//                .alternateTypeRules(newRule(
//                        typeResolver.resolve(Response.class, WildcardType.class),
//                        typeResolver.resolve(WildcardType.class)
//                ))
//                .globalResponseMessage(RequestMethod.GET,
//                        Lists.newArrayList(new ResponseMessageBuilder()
//                                        .code(500)
//                                        .message("500 message")
//                                        .responseModel(new ModelRef("Error"))
//                                        .build(),
//                                new ResponseMessageBuilder()
//                                        .code(403)
//                                        .message("Forbidden!")
//                                        .build()))
                .select()
                    .apis(RequestHandlerSelectors.basePackage("city.commerce.controller.api"))
                    .paths(PathSelectors.ant("/api/**"))
                .build();
    }

    private List<Parameter> globalOperationParameters() {
        return Lists.newArrayList(
                new ParameterBuilder()
                        .name("appKey")
                        .description("App key")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build(),
                new ParameterBuilder()
                        .name("sign")
                        .description("sign request path and body")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build(),
                new ParameterBuilder()
                        .name("token")
                        .description("user token if user has signed in")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("City Commerce")
                .description("City Commerce API")
                .termsOfServiceUrl("http://github.com/yanguanwei/cityCommerce")
                .version("1.0")
                .build();
    }
}
