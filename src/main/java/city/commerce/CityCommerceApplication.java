package city.commerce;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-15
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "city.commerce.init"
})
public class CityCommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CityCommerceApplication.class, args);
    }
}
