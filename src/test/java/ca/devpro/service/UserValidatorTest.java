package ca.devpro.service;

import ca.devpro.api.UserDto;
import ca.devpro.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserValidatorTest {

    private UserValidator userValidator;
    private UserRepository userRepository;

    @BeforeEach
//    public void init() {
//        userRepository = mock(UserRepository.class);
//        when(userRepository.existsByUsernameIgnoreCase(anyString())).thenReturn(false);
//        userValidator = new UserValidator(userRepository);
//    }


    public void init() {
        userRepository = mock(UserRepository.class);
        when(userRepository.existsByUsernameIgnoreCase(anyString())).thenAnswer(invocation -> {
            String suppliedUsername = invocation.getArgument(0);
            if ("someName".equals(suppliedUsername)) {
                return true;
            }
            return false;
        });
        userValidator = new UserValidator(userRepository);
    }

    @Test
    public void testValidate_whenFirstNameBlank_shouldReturnError() {
        UserDto dto = getValidUser().setFirstName(" ");
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.FIRST_NAME_REQUIRED, errors.get("firstName"));
    }
//    @Test
//    public void testValidate_whenFirstNameWrong_shouldReturnError() {
//        UserDto dto = getValidUser().setFirstName("someWrongName");
//        Map<String, String> errors = userValidator.validate(dto);
//        assertEquals(1, errors.size());
//
//    }

    @Test
    public void testValidate_whenLastNameBlank_shouldReturnError() {
        UserDto dto = getValidUser().setLastName(" ");
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.LAST_NAME_REQUIRED, errors.get("lastName"));
    }

//    @Test
//    public void testValidate_whenLastNameWrong_shouldReturnError() {
//        UserDto dto = getValidUser().setLastName("someWrongName");
//        Map<String, String> errors = userValidator.validate(dto);
//        assertEquals(1, errors.size());
//
//    }


    @Test
    public void testValidate_whenUsernameBlank_shouldReturnError() {
        UserDto dto = getValidUser().setUsername(" ");
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.USERNAME_REQUIRED, errors.get("username"));

    }
    @Test
    public void testValidate_whenUsernameTaken_shouldReturnError() {
        UserDto dto = getValidUser().setUsername("someName");
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.USERNAME_TAKEN, errors.get("username"));

    }



    private UserDto getValidUser() {
        return new UserDto()
                .setUsername("doddt")
                .setFirstName("Tim")
                .setLastName("Dodd");
    }
}
