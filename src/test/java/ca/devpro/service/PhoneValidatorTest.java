package ca.devpro.service;

import ca.devpro.api.PhoneDto;
import ca.devpro.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneValidatorTest {

    private PhoneValidator phoneValidator;
    private PhoneRepository phoneRepository;

    @BeforeEach
    public void init() {
        phoneRepository = mock(PhoneRepository.class);
        when(phoneRepository.existsByPhoneNumberIgnoreCase(anyString())).thenAnswer(invocation -> {
            String suppliedPhonenumber = invocation.getArgument(0);
            if ("somePhoneNumber".equals(suppliedPhonenumber)) {
                return true;
            }
            return false;
        });
        phoneValidator = new PhoneValidator(phoneRepository);
    }

    @Test
    public void testValidate_whenPhoneNumberBlank_shouldReturnError() {
        PhoneDto dto = getValidPhone().setPhoneNumber(" ");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(PhoneValidator.PHONE_NUMBER_REQUIRED, errors.get("phonenumber"));
    }

    @Test
    public void testValidate_whenPhoneNumberTaken_shouldReturnError() {
        PhoneDto dto = getValidPhone().setPhoneNumber("somePhoneNumber");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(PhoneValidator.PHONE_NUMBER_TAKEN, errors.get("phonenumber"));

    }

    @Test
    public void testValidate_whenPhoneNumber_LengthIsInvalid_LONG_shouldReturnError() {
        PhoneDto dto = getValidPhone().setPhoneNumber("84629343323131");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(PhoneValidator.PHONE_NUMBER_LENGTH_INVALID, errors.get("phonenumber"));
    }

    @Test
    public void testValidate_whenPhoneNumber_LengthIsInvalid_SHORT_shouldReturnError() {
        PhoneDto dto = getValidPhone().setPhoneNumber("846");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(PhoneValidator.PHONE_NUMBER_LENGTH_INVALID, errors.get("phonenumber"));
    }


    @Test
    public void testValidate_whenPhoneTypeBlank_shouldReturnError() {
        PhoneDto dto = getValidPhone().setPhoneType(" ");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(PhoneValidator.PHONE_TYPE_REQUIRED, errors.get("phoneType"));
    }


    private PhoneDto getValidPhone() {
        return new PhoneDto()
                .setPhoneNumber("3062217649")
                .setPhoneType("cell");

    }



}
