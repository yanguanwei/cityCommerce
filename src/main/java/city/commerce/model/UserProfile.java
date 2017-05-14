package city.commerce.model;

import city.commerce.entity.UserProfileEntity;
import city.commerce.model.error.ErrorCode;
import city.commerce.validation.Validation;
import city.commerce.validation.Validator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-01
 */
@Data
public class UserProfile implements Validator {
    private static final String BIRTHDAY_FORMATTER = "yyyy-MM-dd";

    private String firstName;
    private String lastName;

    @ApiModelProperty(example = "1988-08-02")
    private String birthday;
    private Gender gender;
    private String avatar;

    public UserProfile() {
    }

    public UserProfile(UserProfileEntity userProfileEntity) {
        this.firstName = userProfileEntity.getFirstName();
        this.lastName = userProfileEntity.getLastName();
        this.birthday = userProfileEntity.getBirthday();
        this.gender = Gender.values()[userProfileEntity.getGender()];
        this.avatar = userProfileEntity.getAvatar();
    }


    @Override
    public void validate(Validation validation) {
        String birthday = this.getBirthday();
        if (StringUtils.isNoneBlank(birthday)) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(BIRTHDAY_FORMATTER);
                LocalDate.parse(birthday, dtf);
            } catch (IllegalArgumentException |  DateTimeParseException e) {
                validation.add("birthday", ErrorCode.FIELD_INVALID_FORMATTER);
            }
        }
    }
}
