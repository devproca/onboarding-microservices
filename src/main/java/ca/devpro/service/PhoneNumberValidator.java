package ca.devpro.service;

import ca.devpro.api.PhoneNumberDto;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.PhoneNumberRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class PhoneNumberValidator {

    public static final String PHONE_NUMBER_REQUIRED = "PHONE_NUMBER_REQUIRED";
    public static final String PHONE_NUMBER_EXCEEDS_MAX_LENGTH = "PHONE_NUMBER_EXCEEDS_MAX_LENGTH";
    public static final String PHONE_NUMBER_INVALID = "PHONE_NUMBER_INVALID";

    private final PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public PhoneNumberValidator(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    public void validateAndThrow(PhoneNumberDto dto) {
        Map<String, String> errors = validate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public Map<String, String> validate(PhoneNumberDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();

        validatePhoneNumber(errors, dto);

        return errors;
    }

    private void validatePhoneNumber(Map<String, String> errors, PhoneNumberDto dto) {
        if (StringUtils.isBlank(dto.getPhoneNumber())) {
            errors.put("phoneNumber", PHONE_NUMBER_REQUIRED);
        } else if (dto.getPhoneNumber().length() > 10) {
            errors.put("phoneNumber", PHONE_NUMBER_EXCEEDS_MAX_LENGTH);
        } else if (!NumberUtils.isCreatable(dto.getPhoneNumber())) {
            errors.put("phoneNumber", PHONE_NUMBER_INVALID);
        }
    }
}
