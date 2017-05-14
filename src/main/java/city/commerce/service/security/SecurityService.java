package city.commerce.service.security;

import city.commerce.entity.TokenEntity;
import city.commerce.entity.UserEntity;
import city.commerce.model.api.StatusCode;
import city.commerce.entity.AuthorityEntity;
import city.commerce.model.error.ServiceException;
import city.commerce.model.User;
import city.commerce.model.SignInUser;
import city.commerce.model.params.UserSignInParam;
import city.commerce.repository.AuthorityRepository;
import city.commerce.repository.TokenRepository;
import city.commerce.repository.UserRepository;
import city.commerce.service.UserService;
import city.commerce.validation.Validation;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-30
 */
@Service
public class SecurityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private Map<Integer, SignInUser> map = new ConcurrentHashMap<>();

    private TokenGenerator tokenGenerator = new DefaultTokenGenerator();

    @Autowired
    private TokenRepository tokenRepository;

    private static volatile int EXPIRED = -1;

    public SignInUser signIn(UserSignInParam userSignInParam) {
        Validation.validate(userSignInParam);

        UserEntity userEntity = userService.queryUser(userSignInParam.getName());
        if (null == userEntity) {
            throw new ServiceException(StatusCode.AuthenticationInvalidName);
        }

        if (! userService.encryptPassword(userSignInParam.getPassword()).equals(userEntity.getPassword())) {
            throw new ServiceException(StatusCode.AuthenticationInvalidPassword);
        }

        String token = tokenGenerator.generateToken(userEntity.getId());
        SignInUser signInUser = new SignInUser(userEntity, token);

        tokenRepository.addToken(userEntity.getId(), token);

        return signInUser;
    }

    public TokenEntity signOut(User user) {
        return tokenRepository.deleteToken(user.getUserId());
    }

    public String generateToken(int userId) {
        String token = tokenGenerator.generateToken(userId);
        tokenRepository.addToken(userId, token);
        return token;
    }

    public SignInUser verifyUserToken(String token) {
        if (StringUtils.isNoneBlank(token)) {
            int userId = tokenGenerator.resolveUserId(token);
            if (userId > 0) {
                String expectedToken = tokenRepository.getToken(userId);
                if (StringUtils.equals(expectedToken, token)) {
                    UserEntity userEntity = userService.queryUser(userId);
                    if (null != userEntity) {
                        return new SignInUser(userEntity, token);
                    }
                }
            }
        }

        throw new ServiceException(StatusCode.AuthenticationInvalidToken);
    }

    public void verifySignature(Authentication authentication, String path, Map<String, String> params) {
        Validation.validate(authentication);

        AuthorityEntity authorityEntity = authorityRepository.queryAuthority(authentication.getAppKey());
        if (null == authorityEntity) {
            throw new ServiceException(StatusCode.AuthorityAbsent);
        }

        if (authorityEntity.isVerifySign()) {
            if (EXPIRED > 0 && (System.currentTimeMillis()/1000 - authentication.getTimestamp())>EXPIRED) {
                throw new ServiceException(StatusCode.AuthorityExpired);
            }

            String sign = generateSignature(authorityEntity.getAppSecret(), authentication.getTimestamp(), path, params);
            if (!sign.equals(authentication.getSignature())) {
                throw new ServiceException(StatusCode.AuthenticationInvalidSign);
            }
        }
    }

    public String generateSignature(String appSecret, int timeStamp, String path, Map<String, String> params) {
        StringBuilder builder = new StringBuilder()
                .append(appSecret)
                .append(timeStamp)
                .append(path);

        String sortedParams = new TreeMap<>(params).entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());

        builder.append(sortedParams);

        return Hashing.md5().newHasher()
                .putString(builder.toString(), Charsets.UTF_8)
                .toString();
    }

}
