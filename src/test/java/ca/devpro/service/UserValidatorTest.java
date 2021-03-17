package ca.devpro.service;

import ca.devpro.api.UserDto;
import ca.devpro.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.Map;

public class UserValidatorTest {

    private UserValidator userValidator;
    private static final String DUPLICATE_USERNAME = "duplicateUsername";

    @BeforeEach
    public void init() {
        UserRepository userRepository = mock(UserRepository.class);
        //when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenAnswer(invocation -> {
            String username = invocation.getArgument(0);
            return DUPLICATE_USERNAME.equals(username);
        });

        userValidator = new UserValidator(userRepository);
    }

    // Username Uniqueness Test
    @Test
    public void testValidate_whenDuplicateUsername_shouldReturnError() {
        UserDto dto = newValidUser().setUsername(DUPLICATE_USERNAME);
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.USERNAME_TAKEN, errors.get("username"));
    }

    // Blank Field Tests
    @Test
    public void testValidate_whenFirstNameBlank_shouldReturnError() {
        UserDto dto = newValidUser().setFirstName(" ");
        Map<String, String> errors = userValidator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals(UserValidator.FIRST_NAME_REQUIRED, errors.get("firstName"));
    }

    @Test
    public void testValidate_whenLastNameBlank_shouldReturnError() {
        UserDto dto = newValidUser().setLastName(" ");
        Map<String, String> errors = userValidator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals(UserValidator.LAST_NAME_REQUIRED, errors.get("lastName"));
    }

    @Test
    public void testValidate_whenUsernameBlank_shouldReturnError() {
        UserDto dto = newValidUser().setUsername(" ");
        Map<String, String> errors = userValidator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals(UserValidator.USERNAME_REQUIRED, errors.get("username"));
    }

    // Character Length Tests
    @Test
    public void testValidate_whenFirstNameTooLong_shouldReturnError() {
        UserDto dto = newValidUser().setFirstName("MyFirstNameIsJustTestButIAmAddingCharactersToExceedThe30Limit");
        Map<String, String> errors = userValidator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals(UserValidator.FIRST_NAME_EXCEEDS_MAX_LENGTH, errors.get("firstName"));
    }

    @Test
    public void testValidate_whenLastNameTooLong_shouldReturnError() {
        UserDto dto = newValidUser().setLastName("MyLastNameIsJustUserButIAmAddingCharactersToExceedThe30Limit");
        Map<String, String> errors = userValidator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals(UserValidator.LAST_NAME_EXCEEDS_MAX_LENGTH, errors.get("lastName"));
    }

    @Test
    public void testValidate_whenUsernameTooLong_shouldReturnError() {
        UserDto dto = newValidUser().setUsername("MyUsernameIsJustTestUserButIAmAddingCharactersToExceedThe20Limit");
        Map<String, String> errors = userValidator.validate(dto);

        assertEquals(1, errors.size());
        assertEquals(UserValidator.USERNAME_EXCEEDS_MAX_LENGTH, errors.get("username"));
    }

    // Test Data
    private UserDto newValidUser() {
        return new UserDto()
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testuser");
    }
}
