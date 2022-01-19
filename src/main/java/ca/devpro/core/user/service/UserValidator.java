package ca.devpro.core.user.service;

import ca.devpro.api.UserDto;
import ca.devpro.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UserValidator {

    private static String FIELD_EMAIL_ADDRESS = "emailAddress";
    private static String EMAIL_ADDRESS_REQUIRED = "EMAIL_ADDRESS_REQUIRED";

    public void validateAndThrow(UserDto dto) {
        Map<String, String> errors = validate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public Map<String, String> validate(UserDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (StringUtils.isBlank(dto.getEmailAddress())) {
            errors.put(FIELD_EMAIL_ADDRESS, EMAIL_ADDRESS_REQUIRED);
        }
        //add more things.
        return errors;
    }
}
