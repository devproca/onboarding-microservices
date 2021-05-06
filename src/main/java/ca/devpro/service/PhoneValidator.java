package ca.devpro.service;
import ca.devpro.api.PhoneDto;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.PhoneRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class PhoneValidator {

    static final String PHONE_NUMBER_REQUIRED = "PHONE_NUMBER_REQUIRED";
    static final String PHONE_NUMBER_LENGTH_INVALID = "PHONE_NUMBER_LENGTH_INVALID";
    static final String PHONE_NUMBER_TAKEN = "PHONE_NUMBER_TAKEN";

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
            errors.put("phonenumber", PHONE_NUMBER_REQUIRED);
        } else if(isCreate(dto) && phoneRepository.existsByPhoneNumberIgnoreCase(dto.getPhoneNumber())) {
            errors.put("phonenumber", PHONE_NUMBER_TAKEN);
        } else if (dto.getPhoneNumber().length() !=10){
            errors.put("phonenumber", PHONE_NUMBER_LENGTH_INVALID);
        }
    }

    private boolean isCreate(PhoneDto dto) {
        return dto.getPhoneId() == null;
    }


}
