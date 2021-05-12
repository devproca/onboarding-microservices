package ca.devpro.service;

import ca.devpro.api.PhoneDto;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.PhoneRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class PhoneValidator {

    static final String PHONENUMBER_REQUIRED = "PHONENUMBER_REQUIRED";
    static final String PHONENUMBER_TAKEN = "PHONENUMBER_TAKEN";
    static final String PHONENUMBER_LENGTH_INVALID = "PHONENUMBER_LENGTH_INVALID";
    static final String PHONETYPE_REQUIRED = "PHONETYPE_REQUIRED";

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneValidator(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public void validateAndThrow(PhoneDto dto) {
        Map<String, String> errors = validate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public Map<String, String> validate(PhoneDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        validatePhoneNumber(errors, dto);
        validatePhoneType(errors, dto);
        return errors;
    }

    private void validatePhoneNumber(Map<String, String> errors, PhoneDto dto) {
        if (StringUtils.isBlank(dto.getPhoneNumber())) {
            errors.put("phoneNumber", PHONENUMBER_REQUIRED);
        } else if(isCreate(dto) && phoneRepository.existsByPhoneNumberIgnoreCase(dto.getPhoneNumber())) {
            errors.put("phoneNumber", PHONENUMBER_TAKEN);
        } else if(dto.getPhoneNumber().length() != 10) {
            errors.put("phoneNumber", PHONENUMBER_LENGTH_INVALID);
        }
    }

    private void validatePhoneType(Map<String, String> errors, PhoneDto dto) {
        if (StringUtils.isBlank(dto.getPhoneType())) {
            errors.put("phoneType", PHONETYPE_REQUIRED);
        }
    }

    private boolean isCreate(PhoneDto dto) {
        return dto.getPhoneId() == null;
    }

}
