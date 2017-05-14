package city.commerce.repository;

import city.commerce.entity.TokenEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-04
 */
@Repository
public class TokenRepository {
    private int expire = 86400 * 30;
    private Map<Integer, TokenEntity> map = new ConcurrentHashMap<>();

    public void addToken(int userId, String token) {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setCreated((int) (System.currentTimeMillis() / 1000));
        map.put(userId, tokenEntity);
    }

    public String getToken(int userId) {
        TokenEntity tokenEntity = map.get(userId);
        if (null != tokenEntity) {
            if (System.currentTimeMillis()/1000 - tokenEntity.getCreated() <= expire) {
                return tokenEntity.getToken();
            }
        }
        return null;
    }

    public TokenEntity deleteToken(int userId) {
        TokenEntity tokenEntity = map.remove(userId);
        return tokenEntity;
    }
}
