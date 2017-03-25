package city.commerce.init;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-16
 */
@Configuration
public class MongodbConfiguration {

    @Bean
    public MongoClient mongoClient() throws IOException {
        String bindIp = "127.0.0.1";
        int port = 27017;
        return new MongoClient(bindIp, port);
    }
}
