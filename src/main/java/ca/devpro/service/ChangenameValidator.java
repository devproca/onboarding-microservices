package ca.devpro.service;

import ca.devpro.dto.ChangenameDto;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.ChangenameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ChangenameValidator {

    public static final String CHANGE_NAME_REQUIRED = "CHANGE_NAME_REQUIRED";

    private final ChangenameRepository changenameRepository;
    @Autowired
    public ChangenameValidator(ChangenameRepository changenameRepository) {
        this.changenameRepository = changenameRepository;
    }

    public void validateAndThrow(ChangenameDto dto, String firstName, String lastName) {
        Map<String, String> errors = validate(dto,firstName, lastName);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public Map<String, String> validate(ChangenameDto dto, String firstName, String lastName) {
        Map<String, String> errors = new LinkedHashMap<>();
        validateFirstName(errors, dto, firstName);
        validateLastName(errors, dto, lastName);
        return errors;
    }

    private boolean validateFirstName(Map<String, String> errors, ChangenameDto dto, String firstName) {
        if (dto.getFirstName() == firstName){
            return true;
        } else {
            return false;
        }
    }

    private boolean validateLastName(Map<String, String> errors, ChangenameDto dto, String lastName) {
        if (dto.getLastName() == lastName){
            return true;
        } else {
            return false;
        }
    }

}