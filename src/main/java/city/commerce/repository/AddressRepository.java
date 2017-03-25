package city.commerce.repository;

import city.commerce.entity.Address;
import city.commerce.entity.Location;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-17
 */
@Repository
public class AddressRepository {
    @Autowired
    private MongoClient mongoClient;

    private DBCollection addressCollection;

    private BasicDBList getLocationObject(Location location) {
        BasicDBList list = new BasicDBList();
        list.put(0, location.getLongitude());
        list.put(1, location.getLatitude());
        return list;
    }

    private Address resolveAddress(DBObject object) {
        BasicDBList list = (BasicDBList) object.get("location");
         return new Address();
    }

    public List<Address> near(Location location) {
        BasicDBObject query = new BasicDBObject(
                "location",
                new BasicDBObject(
                        "$near", getLocationObject(location)
                )
        );

        DB db = mongoClient.getDB("cityCommerce");
        DBCollection collection = db.getCollection("address");
        DBCursor cursor = collection.find(query);

        List<Address> addresses = new ArrayList<>();
        try {
            while(cursor.hasNext()) {
                DBObject object = cursor.next();
                System.out.println(object);
                addresses.add(resolveAddress(object));
            }
        } finally {
            cursor.close();
        }

        return addresses;
    }

    private DBCollection getAddressCollection() {
        if (null == addressCollection) {
            DB db = mongoClient.getDB("cityCommerce");

            DBCollection collection = db.getCollection("address");
            collection.createIndex(new BasicDBObject("location", "2d"));

            this.addressCollection = collection;
        }

        return this.addressCollection;
    }

    public void insert(Address address) {
        DBCollection collection = getAddressCollection();

        BasicDBObject document = new BasicDBObject()
                .append("address", address.getStreet())
                .append("location", null);

        collection.insert(document);
    }
}
