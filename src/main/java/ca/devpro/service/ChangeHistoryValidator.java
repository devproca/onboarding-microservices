package ca.devpro.service;

import ca.devpro.api.ChangeHistoryDto;
import ca.devpro.api.UserDto;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.ChangeHistoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChangeHistoryValidator {

    static final String FIRST_NAME_REQUIRED = "FIRST_NAME_REQUIRED";
    static final String LAST_NAME_REQUIRED = "LAST_NAME_REQUIRED";
    static final String USERNAME_REQUIRED = "USERNAME_REQUIRED";
    static final String USERNAME_TAKEN = "USERNAME_TAKEN";

    private final ChangeHistoryRepository changeHistoryRepository;

    @Autowired
    public ChangeHistoryValidator(ChangeHistoryRepository changeHistoryRepository) {
        this.changeHistoryRepository = changeHistoryRepository;
    }

    public void validateAndThrow(ChangeHistoryDto dto) {
        Map<String, String> errors = validate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public Map<String, String> validate(ChangeHistoryDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        validateUpdatedFirstName(errors, dto);
        validateUpdatedLastName(errors, dto);
        validateUpdatedUsername(errors, dto);
        return errors;
    }

    private void validateUpdatedFirstName(Map<String, String> errors, UserDto dto) {
        if (StringUtils.isBlank(dto.getFirstName())) {
            errors.put("firstName", FIRST_NAME_REQUIRED);
        }
    }

    private void validateLastName(Map<String, String> errors, UserDto dto) {
        if (StringUtils.isBlank(dto.getLastName())) {
            errors.put("lastName", LAST_NAME_REQUIRED);
        }
    }

    private void validateUsername(Map<String, String> errors, UserDto dto) {
        if (StringUtils.isBlank(dto.getUsername())) {
            errors.put("username", USERNAME_REQUIRED);
        } else if(isCreate(dto) && userRepository.existsByUsernameIgnoreCase(dto.getUsername())) {
            errors.put("username", USERNAME_TAKEN);
        }
    }

    private boolean isCreate(UserDto dto) {
        return dto.getUserId() == null;
    }

}
