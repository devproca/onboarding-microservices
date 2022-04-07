package ca.devpro.user;

import ca.devpro.api.UserDto;
import ca.devpro.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UserValidator {

    static final String FIELD_FIRST_NAME = "firstName";
    static final String FIELD_LAST_NAME = "lastName";
    static final String FIELD_USERNAME = "username";

    static final String FIRST_NAME_REQUIRED = "FIRST_NAME_REQUIRED";
    static final String FIRST_NAME_GT_50 = "FIRST_NAME_GT_50";
    static final String LAST_NAME_REQUIRED = "LAST_NAME_REQUIRED";


    public void validateAndThrow(UserDto dto) {
        Map<String, String> errors = validate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public Map<String, String> validate(UserDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        populateFirstNameErrors(errors, dto);
        populateLastNameErrors(errors, dto);
        return errors;
    }

    private void populateFirstNameErrors(Map<String, String> errors, UserDto dto) {
        if (StringUtils.isBlank(dto.getFirstName())) {
            errors.put(FIELD_FIRST_NAME, FIRST_NAME_REQUIRED);
        } else if(dto.getFirstName().length() > 50) {
            errors.put(FIELD_FIRST_NAME, FIRST_NAME_GT_50);
        }
    }

    private void populateLastNameErrors(Map<String, String> errors, UserDto dto) {
        if (StringUtils.isBlank(dto.getLastName())) {
            errors.put(FIELD_LAST_NAME, LAST_NAME_REQUIRED);
        }
    }
}
