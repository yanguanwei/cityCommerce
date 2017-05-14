package city.commerce.service.security;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-04
 */
public interface TokenGenerator {
    String generateToken(int userId);
    int resolveUserId(String token);
}
