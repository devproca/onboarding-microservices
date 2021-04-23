package ca.devpro.service;

import ca.devpro.api.PhoneDto;
import ca.devpro.api.UserDto;
import ca.devpro.repository.PhoneRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PhoneValidatorTest {

    private PhoneValidator phoneValidator;
    private PhoneRepository phoneRepository;

    @Test
    public void testValidate_whenPhoneNumberNull_shouldProduceError(){
        PhoneDto dto = getValidPhone().setPhoneNumber(null);
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(PhoneValidator.PHONE_NUMBER_REQUIRED, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneTypeNull_shouldProduceError(){
        PhoneDto dto = getValidPhone().setPhoneType(null);
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(PhoneValidator.PHONE_NUMBER_REQUIRED, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneNumberExceedsCorrectLength_shouldProduceError(){
        PhoneDto dto = getValidPhone().setPhoneNumber(RandomStringUtils.random(17, "1"));
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(PhoneValidator.PHONE_NUMBER_LENGTH, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneTypeExceedsCorrectLength_shouldProduceError(){
        PhoneDto dto = getValidPhone().setPhoneType(RandomStringUtils.random(17, "1"));
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(PhoneValidator.PHONE_TYPE_LENGTH, errors.get("phoneType"));
    }

    private PhoneDto getValidPhone() {
        return new PhoneDto()
                .setPhoneNumber("test")
                .setPhoneType("phone");
    }

}
