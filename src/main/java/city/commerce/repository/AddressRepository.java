package city.commerce.repository;

import city.commerce.entity.Address;
import city.commerce.repository.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @author young.alway@gmail.com
 * @since 2017-03-17
 */
@Repository
public class AddressRepository {
    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private DivisionRepository divisionRepository;

    public Address queryAddress(int id) {
        return addressMapper.find(id);
    }

    public Address queryAddressByPlaceId(String placeId) {
        return addressMapper.findByPlaceId(placeId);
    }

    public Address addAddress(Address address) {
        addressMapper.add(address);
        return address;
    }
}
