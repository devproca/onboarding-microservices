package ca.devpro.service;

import ca.devpro.api.UserDto;

import static org.junit.jupiter.api.Assertions.*;

import ca.devpro.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
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

    @Test
    public void testValidate_whenFirstNameBlank_shouldReturnError() {
        UserDto dto = newValidUser().setFirstName(" ");
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.FIRST_NAME_REQUIRED, errors.get("firstName"));
    }

    @Test
    public void testValidate_whenDuplicateUsername_shouldReturnError() {
        UserDto dto = newValidUser().setUsername(DUPLICATE_USERNAME);
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.USERNAME_TAKEN, errors.get("username"));
    }

    private UserDto newValidUser() {
        return new UserDto()
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testuser");
    }
}
