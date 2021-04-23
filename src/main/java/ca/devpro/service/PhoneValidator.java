package ca.devpro.service;

import ca.devpro.api.PhoneDto;
import ca.devpro.api.UserDto;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.PhoneRepository;
import ca.devpro.repository.PhoneRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class PhoneValidator {

    static final String PHONE_NUMBER_REQUIRED = "PHONE_NUMBER_REQUIRED";
    static final String PHONE_TYPE_REQUIRED = "PHONE_TYPE_REQUIRED";
    static final String PHONE_NUMBER_LENGTH = "PHONE_NUMBER_LENGTH";
    static final String PHONE_TYPE_LENGTH = "PHONE_TYPE_LENGTH";

    private PhoneRepository phoneRepository;

    @Autowired
    public PhoneValidator(PhoneRepository phoneRepository){
        this.phoneRepository = phoneRepository;
    }

    public void validateAndThrow(PhoneDto dto){
        Map<String, String> errors = validate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public Map<String, String> validate(PhoneDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();

        validatePhoneNumberAndPhoneNumberLength(errors, dto);
        validatePhoneTypeAndPhoneTypeLength(errors, dto);
        return errors;
    }

    private void validatePhoneNumberAndPhoneNumberLength(Map<String, String> errors, PhoneDto dto) {
        if (StringUtils.isBlank(dto.getPhoneNumber())) {
            errors.put("phoneNumber", PHONE_NUMBER_REQUIRED);
        } else if (dto.getPhoneNumber().length() > 15) {
            errors.put("phoneNumber", PHONE_NUMBER_LENGTH);
        }
    }

    private void validatePhoneTypeAndPhoneTypeLength(Map<String, String> errors, PhoneDto dto) {
        if (StringUtils.isBlank(dto.getPhoneType())) {
            errors.put("phoneType", PHONE_TYPE_REQUIRED);
        } else if (dto.getPhoneType().length() > 15) {
            errors.put("phoneType", PHONE_TYPE_LENGTH);
        }
    }

}
