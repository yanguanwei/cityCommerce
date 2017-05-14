package city.commerce.service;

import city.commerce.entity.VendorEntity;
import city.commerce.model.api.StatusCode;
import city.commerce.model.error.ServiceException;
import city.commerce.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-16
 */
@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    public VendorEntity queryVendor(int vendorId) {
        VendorEntity vendorEntity = getVendor(vendorId);
        if (null == vendorEntity) {
            throw new ServiceException(StatusCode.ResourceNotFound);
        }

        return vendorEntity;
    }

    public VendorEntity saveVendor(VendorEntity vendorEntity) {
        return vendorRepository.saveVendor(vendorEntity);
    }

    public VendorEntity getVendor(int vendorId) {
        return vendorRepository.queryVendor(vendorId);
    }
}
