package city.commerce.repository;

import city.commerce.entity.StoreEntity;
import city.commerce.repository.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@Repository
public class StoreRepository {

    @Autowired
    private StoreMapper storeMapper;

    public StoreEntity queryStore(int id) {
        return storeMapper.findById(id);
    }

    public StoreEntity queryStoreByPlaceId(String placeId) {
        return storeMapper.findByPlaceId(placeId);
    }

    public StoreEntity addStore(StoreEntity storeEntity) {
        storeMapper.add(storeEntity);
        return storeEntity;
    }

    public StoreEntity updateStore(StoreEntity storeEntity) {
        storeMapper.update(storeEntity);
        return storeEntity;
    }

    public List<StoreEntity> allStores() {
        return null;
    }
}
