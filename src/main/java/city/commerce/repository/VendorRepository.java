package city.commerce.repository;

import city.commerce.entity.VendorEntity;
import city.commerce.repository.mapper.VendorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-12
 */
@Repository
public class VendorRepository {

    @Autowired
    private VendorMapper vendorMapper;

    public VendorEntity queryVendor(int userId) {
        return vendorMapper.find(userId);
    }

    public VendorEntity saveVendor(VendorEntity vendorEntity) {
        VendorEntity exist = vendorMapper.find(vendorEntity.getUserId());
        if (null == exist) {
            vendorMapper.add(vendorEntity);
        } else {
            vendorMapper.update(vendorEntity);
        }
        return vendorEntity;
    }
}
