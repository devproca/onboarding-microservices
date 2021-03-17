package ca.devpro.service;

import ca.devpro.api.PhoneNumberDto;
import ca.devpro.repository.PhoneNumberRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class PhoneNumberValidatorTest {

    private PhoneNumberValidator phoneNumberValidator;

    @BeforeEach
    public void init() {
        PhoneNumberRepository phoneNumberRepository = mock(PhoneNumberRepository.class);
        phoneNumberValidator = new PhoneNumberValidator(phoneNumberRepository);
    }

    @Test
    public void testValidate_whenPhoneNumberBlank_shouldReturnError() {
        PhoneNumberDto dto = newValidPhoneNumber().setPhoneNumber(" ");
        Map<String, String> errors = phoneNumberValidator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals(PhoneNumberValidator.PHONE_NUMBER_REQUIRED, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneNumberTooLong_shouldReturnError() {
        PhoneNumberDto dto = newValidPhoneNumber().setPhoneNumber("30612345678");
        Map<String, String> errors = phoneNumberValidator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals(PhoneNumberValidator.PHONE_NUMBER_EXCEEDS_MAX_LENGTH, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneNumberTooShort_shouldReturnError() {
        PhoneNumberDto dto = newValidPhoneNumber().setPhoneNumber("306123456");
        Map<String, String> errors = phoneNumberValidator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals(PhoneNumberValidator.PHONE_NUMBER_TOO_SHORT, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneNumberIsInvalid_shouldReturnError() {
        PhoneNumberDto dto = newValidPhoneNumber().setPhoneNumber("306123456A");
        Map<String, String> errors = phoneNumberValidator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals(PhoneNumberValidator.PHONE_NUMBER_INVALID, errors.get("phoneNumber"));
    }

    // Test Data
    private PhoneNumberDto newValidPhoneNumber() {
        return new PhoneNumberDto()
                .setPhoneId(UUID.randomUUID())
                .setUserId(UUID.randomUUID())
                .setPhoneNumber("3061234567");
    }
}
