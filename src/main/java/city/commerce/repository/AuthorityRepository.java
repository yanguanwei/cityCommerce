package city.commerce.repository;

import city.commerce.entity.AuthorityEntity;
import city.commerce.repository.mapper.AuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-31
 */
@Repository
public class AuthorityRepository {

    @Autowired
    private AuthorityMapper authorityMapper;

    public AuthorityEntity queryAuthority(String appKey) {
        return authorityMapper.findByAppKey(appKey);
    }
}
