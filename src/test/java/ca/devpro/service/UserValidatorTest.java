package ca.devpro.service;

import ca.devpro.api.UserDto;
import ca.devpro.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserValidatorTest {

    private UserValidator userValidator;
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository = mock(UserRepository.class);
        when(userRepository.existsByUsernameIgnoreCase(anyString())).thenReturn(false);
        userValidator = new UserValidator(userRepository);
    }

    @Test
    public void testValidate_whenFirstNameNull_shouldProduceError() {
        UserDto dto = getValidUser().setFirstName(null);
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.FIRST_NAME_REQUIRED, errors.get("firstName"));
    }

    @Test
    public void testValidate_whenLastNameNull_shouldProduceError(){
        UserDto dto = getValidUser().setLastName(null);
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.LAST_NAME_REQUIRED, errors.get("lastName"));
    }

    @Test
    public void testValidate_whenUserNameNull_shouldProduceError(){
        UserDto dto = getValidUser().setUsername(null);
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.USERNAME_REQUIRED, errors.get("lastName"));
        // assertEquals(expected value, actual value)
    }

    @Test
    public void  testValidate_whenFirstNameExceedsCorrectLength_shouldProduceError(){
        UserDto dto = getValidUser().setFirstName(RandomStringUtils.random(52, "a"));
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.FIRST_NAME_LENGTH, errors.get("firstName"));
    }

    @Test
    public void  testValidate_whenLastNameExceedsCorrectLength_ShouldProduceError(){
        UserDto dto = getValidUser().setLastName(RandomStringUtils.random(52, "a"));
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.LAST_NAME_LENGTH, errors.get("lastName"));
    }

    @Test
    public void  testValidate_whenUsernameExceedsCorrectLength_ShouldProduceError(){
        UserDto dto = getValidUser().setUsername(RandomStringUtils.random(32, "a"));
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.USERNAME_LENGTH, errors.get("username"));
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testuser");
    }
}
