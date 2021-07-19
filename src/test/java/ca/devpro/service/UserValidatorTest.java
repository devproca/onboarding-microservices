package ca.devpro.service;

import ca.devpro.dto.UserDto;
import ca.devpro.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserValidatorTest {

    private UserValidator userValidator;

    @BeforeEach
    public void init() {
// one way to mock
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.existsByUsernameIgnoreCase(anyString())).thenReturn(false);

//fancier way to mock
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByUsernameIgnoreCase(anyString())).then((invocation) -> {
           String username = invocation.getArgument(0);
           if("duplicateuser".equals(username)) {
               return true;
           }
           return false;
        });
        userValidator = new UserValidator(userRepository);
    }

    @Test
    public void testValidateUsername_whenUsernameTaken_shouldReturnError() {
        Map<String, String> errors = userValidator.validate(getValidUser().setUsername("duplicateuser"));

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("username"));
        assertEquals(UserValidator.USERNAME_TAKEN, errors.get("username"));
    }

    @Test
    public void testValidateUsername_whenUsernameAvailable_shouldSucceed() {
        Map<String, String> errors = userValidator.validate(getValidUser().setUsername("availableuser"));

        assertEquals(0, errors.size());
    }

    @Test
    public void testValidateFirstName_whenBlank_shouldReturnError() {
        Map<String, String> errors = userValidator.validate(getValidUser().setFirstName(" "));

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("firstName"));
        assertEquals(UserValidator.FIRST_NAME_REQUIRED, errors.get("firstName"));
    }

    @Test
    public void testValidateLastName_whenNull_shouldReturnError() {
        Map<String, String> errors = userValidator.validate(getValidUser().setLastName(null));

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("lastName"));
        assertEquals(UserValidator.LAST_NAME_REQUIRED, errors.get("lastName"));
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testuser");
    }

}
