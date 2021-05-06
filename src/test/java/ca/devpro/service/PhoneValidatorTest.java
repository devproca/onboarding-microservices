package ca.devpro.service;

import ca.devpro.api.PhoneDto;
import ca.devpro.api.UserDto;
import ca.devpro.client.UserClient;
import ca.devpro.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class PhoneValidatorTest {

    private PhoneValidator phoneValidator;
    private PhoneRepository phoneRepository;
    private UserClient userClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init(){
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);
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
        UserDto userDto = createUser();
        PhoneDto dto = getValidPhone(userDto.getUserId()).setPhoneNumber("12345678");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(phoneValidator.PHONENUMBER_LENGTH_INVALID, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneNumberTooShort_shouldReturnError() {
        UserDto userDto = createUser();
        PhoneDto dto = getValidPhone(userDto.getUserId()).setPhoneNumber("123456");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(phoneValidator.PHONENUMBER_LENGTH_INVALID, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneNumberBlank_shouldReturnError() {
        UserDto userDto = createUser();
        PhoneDto dto = getValidPhone(userDto.getUserId()).setPhoneNumber("");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(phoneValidator.PHONENUMBER_REQUIRED, errors.get("phoneNumber"));
    }

    @Test
    public void testValidate_whenPhoneTypeBlank_shouldReturnError() {
        UserDto userDto = createUser();
        PhoneDto dto = getValidPhone(userDto.getUserId()).setPhoneType(" ");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(phoneValidator.PHONETYPE_REQUIRED, errors.get("phoneType"));
    }

    @Test
    public void testValidate_whenPhoneNumberTaken_shouldReturnError() {
        UserDto userDto = createUser();
        PhoneDto dto = getValidPhone(userDto.getUserId()).setPhoneNumber("8675309");
        Map<String, String> errors = phoneValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(phoneValidator.PHONENUMBER_TAKEN, errors.get("phoneNumber"));
    }

    private PhoneDto getValidPhone(UUID userId) {
        return new PhoneDto()
                .setUserId(userId)
                .setPhoneNumber("1234567")
                .setPhoneType("CELL");
    }

    private UserDto createUser() {
        return userClient.create(getValidUser());
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setUsername("doddt")
                .setFirstName("Tim")
                .setLastName("Dodd");
    }
}
