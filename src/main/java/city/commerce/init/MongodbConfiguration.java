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
    private static final String MONGODB_HOST = "127.0.0.1";
    private static final int MONGODB_PORT = 27017;

    @Bean
    public MongoClient mongoClient() throws IOException {
        return new MongoClient(MONGODB_HOST, MONGODB_PORT);
    }


}
