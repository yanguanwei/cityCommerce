package city.commerce.init;

import city.commerce.entity.Address;
import city.commerce.entity.Location;
import city.commerce.repository.AddressRepository;
import city.commerce.repository.ProductRepository;
import city.commerce.utils.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-15
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "city.commerce.service",
        "city.commerce.controller",
        "city.commerce.repository"
})
public class CityCommerceApplication {
   // @Autowired
   // private ProductRepository productRepository;

    @Autowired
    private AddressRepository addressRepository;

    public static void main(String[] args) {
        SpringApplication.run(CityCommerceApplication.class, args);
    }
}
