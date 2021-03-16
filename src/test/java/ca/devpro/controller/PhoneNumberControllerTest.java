package ca.devpro.controller;

import ca.devpro.api.PhoneNumberDto;
import ca.devpro.api.UserDto;
import ca.devpro.client.PhoneNumberClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PhoneNumberControllerTest {

    @LocalServerPort
    private int port;

    private PhoneNumberClient phoneNumberClient;

    @BeforeEach
    public void init() {
        phoneNumberClient = new PhoneNumberClient();
        phoneNumberClient.setBaseUri("http://localhost:" + port);
    }

    // Create Tests
    @Test
    public void testCreate_whenValidUser_shouldCreatePhoneNumberSuccessfully() {
        UserDto userDto = newValidUserForCreate();
        PhoneNumberDto phoneDto = newValidPhoneNumberForCreate(userDto.getUserId());
        PhoneNumberDto createdDto = phoneNumberClient.create(phoneDto);

        assertNotNull(createdDto.getPhoneId());
    }

    @Test
    public void testCreate_whenValidUser_whenInvalidPhoneNum_shouldReturnError() {
        UserDto userDto = newValidUserForCreate();
        PhoneNumberDto phoneDto = newValidPhoneNumberForCreate(userDto.getUserId()).setPhoneNumber(" ");

        assertThrows(BadRequestException.class, () -> phoneNumberClient.create(phoneDto));
    }

    // Find Tests
    @Test
    public void testFindSinglePhoneNumber_shouldReturnPhoneNumberSuccessfully() {
        UserDto userDto = newValidUserForCreate();
        PhoneNumberDto phoneDto = newValidPhoneNumberForCreate(userDto.getUserId());
        PhoneNumberDto createdDto = phoneNumberClient.create(phoneDto);
        String foundNumber = phoneNumberClient.get(createdDto.getUserId(), createdDto.getPhoneId()).getPhoneNumber();

        assertEquals("3061234567", foundNumber);
    }

    @Test
    public void testFindALlPhoneNumbers_shouldReturnMultiplePhoneNumbersSuccessfully() {
        UserDto userDto = newValidUserForCreate();
        PhoneNumberDto phoneDtoOne = newValidPhoneNumberForCreate(userDto.getUserId());
        PhoneNumberDto phoneDtoTwo = newValidPhoneNumberForCreate(userDto.getUserId()).setPhoneNumber("3067654321");

        phoneNumberClient.create(phoneDtoOne);
        phoneNumberClient.create(phoneDtoTwo);

        assertEquals("3061234567", phoneDtoOne.getPhoneNumber());
        assertEquals("3067654321", phoneDtoTwo.getPhoneNumber());
        assertEquals(2, phoneNumberClient.findAll(userDto.getUserId()).size());
    }

    // Update Tests
    @Test
    public void testUpdate_whenValidUser_shouldCreateThenUpdatePhoneNumber() {
        UserDto userDto = newValidUserForCreate();
        PhoneNumberDto phoneDto = newValidPhoneNumberForCreate(userDto.getUserId());
        PhoneNumberDto createdDto = phoneNumberClient.create(phoneDto);

        assertEquals("3061234567", createdDto.getPhoneNumber());
        PhoneNumberDto updatedDto = phoneNumberClient.update(createdDto.setPhoneNumber("3067654321"));
        assertEquals(createdDto, updatedDto);
    }

    @Test
    public void testUpdate_whenMultiplePhoneNumbers_shouldCreateThenUpdateOnlyOneNumber() {
        UserDto userDto = newValidUserForCreate();
        PhoneNumberDto phoneDtoOne = newValidPhoneNumberForCreate(userDto.getUserId());
        PhoneNumberDto phoneDtoTwo = newValidPhoneNumberForCreate(userDto.getUserId()).setPhoneNumber("3067654321");

        PhoneNumberDto createdDtoOne = phoneNumberClient.create(phoneDtoOne);
        PhoneNumberDto createdDtoTwo = phoneNumberClient.create(phoneDtoTwo);
        assertEquals("3067654321", createdDtoTwo.getPhoneNumber());

        createdDtoTwo.setPhoneNumber("3062143657");
        assertEquals("3061234567", createdDtoOne.getPhoneNumber());
        assertEquals("3062143657", createdDtoTwo.getPhoneNumber());
    }

    // Delete Tests
    @Test
    public void testDelete_whenNumberDeleted_shouldReturnError() {
        UserDto userDto = newValidUserForCreate();
        PhoneNumberDto phoneDto = newValidPhoneNumberForCreate(userDto.getUserId());
        PhoneNumberDto createdDto = phoneNumberClient.create(phoneDto);

        phoneNumberClient.delete(createdDto.getUserId(), createdDto.getPhoneId());
        assertThrows(NotFoundException.class, () -> phoneNumberClient.get(createdDto.getUserId(), createdDto.getPhoneId()));
    }

    @Test
    public void testDelete_whenMultiplePhoneNumbers_shouldCreateThenDeleteOnlyOneNumber() {
        UserDto userDto = newValidUserForCreate();
        PhoneNumberDto phoneDtoOne = newValidPhoneNumberForCreate(userDto.getUserId());
        PhoneNumberDto phoneDtoTwo = newValidPhoneNumberForCreate(userDto.getUserId()).setPhoneNumber("3067654321");

        PhoneNumberDto createdDtoOne = phoneNumberClient.create(phoneDtoOne);
        PhoneNumberDto createdDtoTwo = phoneNumberClient.create(phoneDtoTwo);
        assertEquals("3067654321", createdDtoTwo.getPhoneNumber());

        phoneNumberClient.delete(createdDtoTwo.getUserId(), createdDtoTwo.getPhoneId());
        assertEquals("3061234567", createdDtoOne.getPhoneNumber());
        assertThrows(NotFoundException.class, () -> phoneNumberClient.get(createdDtoTwo.getUserId(), createdDtoTwo.getPhoneId()));
    }

    // Test Data
    private UserDto newValidUserForCreate() {
        return new UserDto()
                .setUserId(UUID.randomUUID())
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testuser");
    }

    private PhoneNumberDto newValidPhoneNumberForCreate(UUID userId) {
        return new PhoneNumberDto()
                .setUserId(userId)
                .setPhoneNumber("3061234567");
    }
}
