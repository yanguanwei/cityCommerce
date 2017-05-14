package city.commerce.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-29
 */
public class Message {

    @Autowired
    private MessageSource source;

    private static Message message;

    public Message() {
        message = this;
    }

    public static String of(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            return message.source.getMessage(key, args, locale);
        } catch (NoSuchMessageException ex) {
            return key;
        }
    }
}
