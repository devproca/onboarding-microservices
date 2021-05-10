package ca.devpro.service;

import ca.devpro.api.ChangeHistoryDto;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.ChangeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ChangeHistoryValidator {

    static final String FIRST_NAME_NOT_CHANGED = "FIRST_NAME_NOT_CHANGED";
    static final String LAST_NAME_NOT_CHANGED = "LAST_NAME_NOT_CHANGED";
    static final String USERNAME_NOT_CHANGED = "USERNAME_NOT_CHANGED";


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

    private void validateUpdatedFirstName(Map<String, String> errors, ChangeHistoryDto dto) {
        if (dto.getPreviousFirstName().equals(dto.getUpdatedFirstName())) {
            errors.put("updatedFirstName", FIRST_NAME_NOT_CHANGED);
        }
    }

    private void validateUpdatedLastName(Map<String, String> errors, ChangeHistoryDto dto) {
        if (dto.getPreviousLastName().equals(dto.getUpdatedLastName())) {
            errors.put("updatedLastName", LAST_NAME_NOT_CHANGED);
        }
    }

    private void validateUpdatedUsername(Map<String, String> errors, ChangeHistoryDto dto) {
        if (dto.getPreviousLastName().equals(dto.getUpdatedLastName())) {
            errors.put("updatedUsername", USERNAME_NOT_CHANGED);
        }

    }


}
