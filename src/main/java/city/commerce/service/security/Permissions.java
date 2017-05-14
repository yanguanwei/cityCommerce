package city.commerce.service.security;

import city.commerce.model.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-04
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permissions {
    long[] value() default {Permission.User};
}
