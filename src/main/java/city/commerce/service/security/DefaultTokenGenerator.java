package city.commerce.service.security;

import java.util.Random;
import java.util.UUID;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-04
 */
public class DefaultTokenGenerator implements TokenGenerator {
    private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };


    private static Random random = new Random(System.currentTimeMillis());

    private String generateShortUuid() {
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    @Override
    public String generateToken(int userId) {
        String shortUuid = generateShortUuid();
        String userIdHex = Integer.toHexString(userId);

        StringBuilder tokenBuilder = new StringBuilder();

        for (int i=0, n1=shortUuid.length(), n2=userIdHex.length(); i<n1; i++) {
            tokenBuilder.append(shortUuid.charAt(i));
            if (i<n2) {
                tokenBuilder.append(userIdHex.charAt(i));
            } else {
                tokenBuilder.append(chars[random.nextInt(20)+6]);
            }
        }

        return tokenBuilder.toString();
    }

    @Override
    public int resolveUserId(String token) {
        StringBuilder userIdHex = new StringBuilder();
        for (int i=1, n=token.length(); i<n; i=i+2) {
            char c = token.charAt(i);
            if (c > 'f' && c <= 'z') {
                break;
            } else {
                userIdHex.append(c);
            }
        }

        if (userIdHex.length() > 0) {
            try {
                return Integer.parseInt(userIdHex.toString(), 16);
            } catch (NumberFormatException e) {
                //ignore
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        ;

        TokenGenerator tg = new DefaultTokenGenerator();

        for (int i=0; i< 10; i++) {
            int userId = random.nextInt(Integer.MAX_VALUE);
            String token = tg.generateToken(userId);
            System.out.println(userId);
            System.out.println(token);
            System.out.println(tg.resolveUserId(token));
            System.out.println("---------------------------");
        }

    }
}
