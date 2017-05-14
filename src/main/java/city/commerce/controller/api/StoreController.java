package city.commerce.controller.api;

import city.commerce.controller.api.response.Response;
import city.commerce.entity.Address;
import city.commerce.entity.DivisionEntity;
import city.commerce.model.DivisionLevel;
import city.commerce.entity.StoreEntity;
import city.commerce.model.Permission;
import city.commerce.model.Store;
import city.commerce.model.api.StatusCode;
import city.commerce.model.params.StoreParam;
import city.commerce.repository.AddressRepository;
import city.commerce.repository.DivisionRepository;
import city.commerce.sdk.google.GooglePlacesService;
import city.commerce.service.StoreService;
import city.commerce.service.search.SearchService;
import city.commerce.service.security.Permissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-25
 */
@RestController
@RequestMapping("/api/stores")
@Api(description = "Store Manager", tags = {"stores"})
public class StoreController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private SearchService searchService;


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Permissions({Permission.Admin})
    public Response<Store> saveStore(StoreParam storeParam) {
        StoreEntity storeEntity = storeService.saveStore(storeParam);
        return Response.ok(storeService.convert(storeEntity));
    }

    @RequestMapping(value = "/{storeId}", method = RequestMethod.GET)
    public Response<Store> queryStore(@PathVariable int storeId) {
        return Response.ok(storeService.convert(storeService.queryStore(storeId)));
    }

    @RequestMapping(value = "/add/{placeId}", method = RequestMethod.POST)
    @ApiOperation(value = "Add Store located by Google Place Id", response = StoreEntity.class)
    public Response<Store> addPlace(@PathVariable("placeId") String placeId) {
        StoreEntity storeEntity = storeService.getStoreByPlaceId(placeId);

        if (null == storeEntity) {
            GooglePlacesService.Place place = GooglePlacesService.getPlaceDetail(placeId);

            List<DivisionEntity> divisionEntities = new ArrayList<>();

            DivisionEntity country = null;
            if (null != place.getCountry()) {
                country = divisionRepository.queryDivisionByName(place.getCountry());
                if (null == country) {
                    country = new DivisionEntity();
                    country.setName(place.getCountry());
                    country.setLevel(DivisionLevel.Country.getLevel());
                    country = divisionRepository.addDivision(country);
                }
                divisionEntities.add(country);
            }

            DivisionEntity province = null;
            if (null != place.getProvince()) {
                province = divisionRepository.queryDivisionByName(place.getProvince());
                if (null == province) {
                    province = new DivisionEntity();
                    province.setName(place.getProvince());
                    province.setLevel(DivisionLevel.Province.getLevel());
                    if (null != country) {
                        province.setParentId(country.getId());
                    }
                    province = divisionRepository.addDivision(province);
                }
                divisionEntities.add(province);
            }

            DivisionEntity city = null;
            if (null != place.getCity()) {
                city = divisionRepository.queryDivisionByName(place.getCity());
                if (null == city) {
                    city = new DivisionEntity();
                    city.setName(place.getCity());
                    city.setLevel(DivisionLevel.City.getLevel());

                    if (null != province) {
                        city.setParentId(province.getId());
                    }

                    city = divisionRepository.addDivision(city);
                }
                divisionEntities.add(city);
            }

            DivisionEntity county = null;
            if (null != place.getCounty()) {
                county = divisionRepository.queryDivisionByName(place.getCountry());
                if (null == county) {
                    county = new DivisionEntity();
                    county.setName(place.getCity());
                    county.setLevel(DivisionLevel.County.getLevel());
                    if (null != city) {
                        county.setParentId(city.getId());
                    }
                    county = divisionRepository.addDivision(county);
                }
                divisionEntities.add(county);
            }

            try {
                storeEntity = addStore(place, divisionEntities);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Response.ok(storeService.convert(storeEntity));
    }

    @Transactional
    private StoreEntity addStore(GooglePlacesService.Place place, List<DivisionEntity> divisionEntities) throws IOException {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setName(place.getName());
        if (CollectionUtils.isNotEmpty(divisionEntities)) {
            DivisionEntity divisionEntity = divisionEntities.get(divisionEntities.size()-1);
            storeEntity.setDivisionId(divisionEntity.getId());
        }

        storeEntity.setLatitude(String.valueOf(place.getLatitude()));
        storeEntity.setLongitude(String.valueOf(place.getLongitude()));
        storeEntity.setPlaceId(place.getPlaceId());
        storeEntity.setStreet(place.getStreet());

        storeEntity = storeService.saveStore(storeEntity);

        searchService.addIndex(storeEntity, divisionEntities);

        return storeEntity;
    }
}
