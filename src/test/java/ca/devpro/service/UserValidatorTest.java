package ca.devpro.service;

import ca.devpro.api.UserDto;
import ca.devpro.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserValidatorTest {

    private UserValidator userValidator;

    @BeforeEach
    public void init() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByUsernameIgnoreCase(anyString())).thenAnswer(invocation -> {
            String username = invocation.getArgument(0, String.class);
            return "takenuser".equals(username);
        });
        userValidator = new UserValidator(userRepository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testValidate_whenFirstNameBlank_shouldReturnError(String value) {
        UserDto dto = getValidUser().setFirstName(value);
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("firstName"));
        assertEquals(UserValidator.FIRST_NAME_REQUIRED, errors.get("firstName"));
    }

    @Test
    public void testValidate_whenLastNameBlank_shouldReturnError() {
        UserDto dto = getValidUser().setLastName(null);
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("lastName"));
        assertEquals(UserValidator.LAST_NAME_REQUIRED, errors.get("lastName"));
    }

    @Test
    public void testValidate_whenUsernameTaken_shouldReturnError() {
        UserDto dto = getValidUser().setUsername("takenuser");
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("username"));
        assertEquals(UserValidator.USERNAME_TAKEN, errors.get("username"));
    }


    private UserDto getValidUser() {
        return new UserDto()
                .setLastName("testlast")
                .setFirstName("testfirst")
                .setUsername("testuser");
    }

}
