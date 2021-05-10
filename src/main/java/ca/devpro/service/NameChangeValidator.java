package ca.devpro.service;

import ca.devpro.api.NameChangeDto;
import ca.devpro.api.UserDto;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.NameChangeRepository;
import ca.devpro.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class NameChangeValidator {

    static final String NAME_CHANGE_REQUIRED = "NAME_CHANGE_REQUIRED";

    private final NameChangeRepository nameChangeRepository;

    @Autowired
    public NameChangeValidator(NameChangeRepository nameChangeRepository) {
        this.nameChangeRepository = nameChangeRepository;
    }

    public void validateAndThrow(NameChangeDto dto) {
        Map<String, String> errors = validate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public Map<String, String> validate(NameChangeDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (!userNameChanged(dto) && !firstNameChanged(dto) && !lastNameChanged(dto)){
            errors.put("nameChange",NAME_CHANGE_REQUIRED);
        }
        return errors;
    }

    private boolean userNameChanged(NameChangeDto dto) {
        if (dto.getPreviousUsername() == dto.getPreviousUsername()){
            return false;
        } else {
            return true;
        }
    }

    private boolean firstNameChanged(NameChangeDto dto) {
        if (dto.getPreviousFirstName() == dto.getPreviousFirstName()){
            return false;
        } else {
            return true;
        }
    }

    private boolean lastNameChanged(NameChangeDto dto) {
        if (dto.getPreviousLastName() == dto.getPreviousLastName()){
            return false;
        } else {
            return true;
        }
    }
}
