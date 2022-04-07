package ca.devpro.user;

import ca.devpro.api.UserDto;
import liquibase.pro.packaged.U;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class UserValidatorTest {

    private UserValidator userValidator;

    @BeforeEach
    public void init() {
        userValidator = new UserValidator();
    }

    @Test
    public void testValidate_whenFirstNameBlank_shouldReturnError() {
        UserDto dto = getValidUser().setFirstName(" ");
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.FIRST_NAME_REQUIRED, errors.get(UserValidator.FIELD_FIRST_NAME));
    }

    @Test
    public void testValidate_whenFirstNameGt50_shouldReturnError() {
        UserDto dto = getValidUser().setFirstName(RandomStringUtils.randomAlphabetic(51));
        Map<String, String> errors = userValidator.validate(dto);
        assertEquals(1, errors.size());
        assertEquals(UserValidator.FIRST_NAME_GT_50, errors.get(UserValidator.FIELD_FIRST_NAME));
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testuser");
    }

}
