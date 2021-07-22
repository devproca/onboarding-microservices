package ca.devpro.service;

import ca.devpro.dto.UserDto;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UserValidator {

    public static final String FIRST_NAME_REQUIRED = "FIRST_NAME_REQUIRED";
    public static final String LAST_NAME_REQUIRED = "LAST_NAME_REQUIRED";
    public static final String USERNAME_REQUIRED = "USERNAME_REQUIRED";
    public static final String USERNAME_TAKEN = "USERNAME_TAKEN";

    public static final String FIRST_NAME_LENGTH_MAX = "FIRST_NAME_LENGTH_MAX";
    public static final String LAST_NAME_LENGTH_MAX = "LAST_NAME_LENGTH_MAX";
    public static final String USERNAME_LENGTH_MAX = "USERNAME_LENGTH_MAX";

    private final UserRepository userRepository;

    private final int firstNameMax = 20;
    private final int lastNameMax = 20;
    private final int userNameMax = 20;
    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void validateAndThrow(UserDto dto) {
        Map<String, String> errors = validate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public Map<String, String> validate(UserDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        validateFirstName(errors, dto);
        validateLastName(errors, dto);
        validateUsername(errors, dto);
        return errors;
    }

    private void validateFirstName(Map<String, String> errors, UserDto dto) {
        if (StringUtils.isBlank(dto.getFirstName())) {
            errors.put("firstName", FIRST_NAME_REQUIRED);
        } else if (dto.getFirstName().length() > firstNameMax){
            errors.put("firstName", FIRST_NAME_LENGTH_MAX);
        }
    }

    private void validateLastName(Map<String, String> errors, UserDto dto) {
        if (StringUtils.isBlank(dto.getLastName())) {
            errors.put("lastName", LAST_NAME_REQUIRED);
        } else if (dto.getLastName().length() > lastNameMax){
            errors.put("lastName", LAST_NAME_LENGTH_MAX);
        }
    }

    private void validateUsername(Map<String, String> errors, UserDto dto) {
        if (StringUtils.isBlank(dto.getUsername())) {
            errors.put("username", USERNAME_REQUIRED);
        } else if(isCreate(dto) && userRepository.existsByUsernameIgnoreCase(dto.getUsername())) {
            errors.put("username", USERNAME_TAKEN);
        } else if (dto.getUsername().length() > userNameMax){
            errors.put("username", USERNAME_LENGTH_MAX);
        }
    }

    private boolean isCreate(UserDto dto) {
        return dto.getUserId() ==  null;
    }
}
