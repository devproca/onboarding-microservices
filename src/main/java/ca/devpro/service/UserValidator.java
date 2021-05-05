package ca.devpro.service;

import ca.devpro.api.UserDto;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UserValidator {
    @Min(1)
    @Max(50)
    static final String FIRST_NAME_REQUIRED = "FIRST_NAME_REQUIRED";
    static final String FIRST_NAME_INVALID_LENGTH = "FIRST_NAME_INVALID_LENGTH";
    @Min(1)
    @Max(50)
    static final String LAST_NAME_REQUIRED = "LAST_NAME_REQUIRED";
    static final String LAST_NAME_INVALID_LENGTH = "LAST_NAME_INVALID_LENGTH";
    @Min(1)
    @Max(50)
    static final String USERNAME_REQUIRED = "USERNAME_REQUIRED";
    static final String USERNAME_INVALID_LENGTH = "USERNAME_INVALID_LENGTH";
    static final String USERNAME_TAKEN = "USERNAME_TAKEN";


    private final UserRepository userRepository;

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
        } else if (dto.getFirstName().length() >50  || dto.getFirstName().length() <1  ){
            errors.put("firstName", FIRST_NAME_INVALID_LENGTH);
        }
    }

    private void validateLastName(Map<String, String> errors, UserDto dto) {
        if (StringUtils.isBlank(dto.getLastName())) {
            errors.put("lastName", LAST_NAME_REQUIRED);
        } else if (dto.getLastName().length() >50  || dto.getLastName().length() <1 ){
            errors.put("lastName", LAST_NAME_INVALID_LENGTH);
        }
    }

    private void validateUsername(Map<String, String> errors, UserDto dto) {
        if (StringUtils.isBlank(dto.getUsername())) {
            errors.put("username", USERNAME_REQUIRED);
        } else if(isCreate(dto) && userRepository.existsByUsernameIgnoreCase(dto.getUsername())) {
            errors.put("username", USERNAME_TAKEN);
        } else if (dto.getUsername().length() >50  || dto.getUsername().length() <1 ){
            errors.put("lastName", USERNAME_INVALID_LENGTH);
        }
    }

    private boolean isCreate(UserDto dto) {
        return dto.getUserId() == null;
    }

}
