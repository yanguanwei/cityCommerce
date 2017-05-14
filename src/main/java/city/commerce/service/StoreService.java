package city.commerce.service;

import city.commerce.controller.api.response.Response;
import city.commerce.entity.StoreEntity;
import city.commerce.entity.VendorEntity;
import city.commerce.model.Division;
import city.commerce.model.Store;
import city.commerce.model.api.StatusCode;
import city.commerce.model.error.ErrorCode;
import city.commerce.model.error.ServiceException;
import city.commerce.model.params.StoreParam;
import city.commerce.repository.StoreRepository;
import city.commerce.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-16
 */
@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private VendorService vendorService;

    public StoreEntity queryStore(int storeId) {
        StoreEntity storeEntity = storeRepository.queryStore(storeId);
        if (null == storeEntity) {
            throw new ServiceException(StatusCode.ResourceNotFound);
        }

        return storeEntity;
    }

    public StoreEntity getStore(int storeId) {
        return  storeRepository.queryStore(storeId);
    }

    public StoreEntity getStoreByPlaceId(String placeId) {
        return storeRepository.queryStoreByPlaceId(placeId);
    }

    public StoreEntity saveStore(StoreParam storeParam) {
        StoreEntity storeEntity = storeParam.toStoreEntity();
        return saveStore(storeEntity);
    }

    public StoreEntity saveStore(StoreEntity storeEntity) {
        VendorEntity vendorEntity = vendorService.getVendor(storeEntity.getVendorId());
        if (null == vendorEntity) {
            throw new ValidationException("vendorId", ErrorCode.FIELD_INVALID);
        }

        if (storeEntity.getId() > 0) {
            storeEntity = storeRepository.addStore(storeEntity);
        } else {
            storeEntity = storeRepository.updateStore(storeEntity);
        }

        return storeEntity;
    }

    public Store convert(StoreEntity storeEntity) {
        Store store = new Store();
        store.setId(storeEntity.getId());
        store.setName(storeEntity.getName());
        store.setVendorId(storeEntity.getVendorId());

        store.setPlaceId(storeEntity.getPlaceId());
        store.setDivisionId(storeEntity.getDivisionId());
        store.setStreet(storeEntity.getStreet());

        List<Division> divisions = divisionService.queryDivisionParentPath(storeEntity.getDivisionId())
                .stream().map(Division::new).collect(Collectors.toList());
        store.setDivisions(divisions);

        return store;
    }
}
