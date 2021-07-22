package ca.devpro.service;

import ca.devpro.dto.PhoneDto;
import ca.devpro.repository.PhoneRepository;
import ca.devpro.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PhoneValidatorTest {

    private PhoneValidator phoneValidator;
    @BeforeEach
    public void init() {
        testValidatePhoneNUmber_whenPhoneNUmberTaken_shouldReturnError();
    }
    @Test
    public void testValidatePhoneNUmber_whenPhoneNUmberTaken_shouldReturnError() {
        PhoneRepository phoneRepository = mock(PhoneRepository.class);
        when(phoneRepository.existsByPhoneNumberIgnoreCase(anyString())).then((invocation) -> {
            String phone_number = invocation.getArgument(0);
            if("123456789".equals(phone_number)) {
                return true;
            }
            return false;
        });
        phoneValidator = new PhoneValidator(phoneRepository);
    }
    @Test
    public void testValidatePhoneNumber_whenBlank_shouldReturnError() {

        Map<String, String> errors =phoneValidator.validate(getValidPhone().setPhoneNumber(" "));

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("phoneNumber"));
        assertEquals(PhoneValidator.PHONE_NUMBER_REQUIRED, errors.get("phoneNumber"));
    }


    @Test
    public void testValidatePhoneNumber_whenNull_shouldReturnError() {
        Map<String, String> errors = phoneValidator.validate(getValidPhone().setPhoneNumber(null));

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("phoneNumber"));
        assertEquals(PhoneValidator.PHONE_NUMBER_REQUIRED, errors.get("phoneNumber"));
    }

    @Test
    public void testValidatePhoneNumber_MaxExceeded_shouldReturnError() {

        PhoneDto dto = getValidPhone().setPhoneNumber(RandomStringUtils.random(23, "1"));
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("phoneNumber"));
        assertEquals(PhoneValidator.PHONE_NUMBER_LENGTH_MAX, errors.get("phoneNumber"));
    }

    private PhoneDto getValidPhone() {
        return new PhoneDto()
                .setPhoneNumber("123456789");
    }
}
