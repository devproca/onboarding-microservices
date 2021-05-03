package ca.devpro.service;

import ca.devpro.api.UserDto;
import static org.junit.jupiter.api.Assertions.*;

import ca.devpro.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.Map;

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
    public void testValidate_whenFirstNameBlank_shouldReturnError() {
        UserDto dto = getValidUser().setFirstName(" ");
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.FIRST_NAME_REQUIRED, errors.get("firstName"));
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setUsername("doddt")
                .setFirstName("Tim")
                .setLastName("Dodd");
    }
}
