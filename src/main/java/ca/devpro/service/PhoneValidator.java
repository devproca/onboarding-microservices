package ca.devpro.service;

import ca.devpro.dto.PhoneDto;
import ca.devpro.dto.UserDto;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.PhoneRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class PhoneValidator {


    public static final String PHONE_NUMBER_REQUIRED = "PHONE_NUMBER_REQUIRED";
    public static final String PHONE_NUMBER_LENGTH_MAX = "PHONE_NUMBER_LENGTH_MAX";
    public static final String PHONE_NUMBER_TAKEN = "PHONE_NUMBER_TAKEN";
    private static final int PHONE_NUMBER_MAX = 15;


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
        return errors;
    }

    private void validatePhoneNumber(Map<String, String> errors, PhoneDto dto) {
        if (StringUtils.isBlank(dto.getPhoneNumber())) {
            errors.put("phoneNumber", PHONE_NUMBER_REQUIRED);
        } else if (dto.getPhoneNumber().length() > PHONE_NUMBER_MAX){
            errors.put("phoneNumber", PHONE_NUMBER_LENGTH_MAX);
        } else if(isCreate(dto) && phoneRepository.existsByPhoneNumberIgnoreCase(dto.getPhoneNumber())) {
            errors.put("phoneNumber", PHONE_NUMBER_TAKEN);
        }
    }

    private boolean isCreate(PhoneDto dto) {
        return dto.getPhoneId() ==  null;
    }




}

