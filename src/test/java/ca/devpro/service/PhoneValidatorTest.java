package ca.devpro.service;

import ca.devpro.api.PhoneDto;
import ca.devpro.api.UserDto;
import ca.devpro.repository.PhoneRepository;
import ca.devpro.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PhoneValidatorTest {

    private PhoneValidator phoneValidator;
    private PhoneRepository phoneRepository;

    @BeforeEach
    public void init(){
        phoneRepository = mock(PhoneRepository.class);
        when(phoneRepository.existsByPhoneNumberIgnoreCase(anyString())).thenAnswer(invocation -> {
            String suppliedPhoneNumber = invocation.getArgument(0);
            if ("8675309".equals(suppliedPhoneNumber)) {
                return true;
            }
            return false;
        });
        phoneValidator = new PhoneValidator(phoneRepository);
    }

    @Test
    public void testValidate_whenPhoneNumberTooLong_shouldReturnError() {
        PhoneDto dto = getValidPhone().setPhoneNumber("12345678");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(phoneValidator.PHONENUMBER_LENGTH_INVALID, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneNumberTooShort_shouldReturnError() {
        PhoneDto dto = getValidPhone().setPhoneNumber("123456");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(phoneValidator.PHONENUMBER_LENGTH_INVALID, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneNumberBlank_shouldReturnError() {
        PhoneDto dto = getValidPhone().setPhoneNumber("");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(phoneValidator.PHONENUMBER_REQUIRED, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneNumberTaken_shouldReturnError() {
        PhoneDto dto = getValidPhone().setPhoneNumber("8675309");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(phoneValidator.PHONENUMBER_TAKEN, errors.get("phoneNumber"));
    }

    private PhoneDto getValidPhone() {
        return new PhoneDto()
                .setPhoneNumber("1234567");
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setUsername("doddt")
                .setFirstName("Tim")
                .setLastName("Dodd");
    }
}
