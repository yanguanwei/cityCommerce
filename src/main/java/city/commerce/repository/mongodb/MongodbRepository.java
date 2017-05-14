package city.commerce.repository.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-26
 */
@Repository
public class MongodbRepository {

    @Autowired
    private MongoClient mongoClient;

    private static final String DB_NAME = "cityCommerce";

    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(DB_NAME);
    }
}
