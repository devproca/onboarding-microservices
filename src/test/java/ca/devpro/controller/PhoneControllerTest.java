package ca.devpro.controller;

import ca.devpro.api.PhoneDto;
import ca.devpro.api.UserDto;
import ca.devpro.client.UserClient;
import ca.devpro.service.SmsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StreamUtils;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PhoneControllerTest {

    private UserClient userClient;

    @LocalServerPort
    private int port;

    @MockBean
    private SmsService smsService;

    @BeforeEach
    public void init() {
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);
    }

    @Test
    public void testCreate_whenValid_shouldPopulatePhoneId() {
        try {
        UserDto userDto = userClient.create(getValidUser());
        PhoneDto dto = getValidPhone(userDto.getUserId());
        PhoneDto createdDto = userClient.createPhone(dto);
        assertNotNull(createdDto.getPhoneId());
        } catch(ClientErrorException e) {
            printError(e);
        }
    }

    @Test
    public void testCreate_whenInvalid_shouldReturnBadRequest() {
        PhoneDto dto = getValidPhone(UUID.randomUUID()).setPhoneNumber(" ");
        assertThrows(BadRequestException.class, () -> userClient.createPhone(dto));
    }

    @Test
    public void testCreateAndGet_whenValid_shouldReturnSameObjects() {
        try {
            UUID userId = userClient.create(getValidUser()).getUserId();
            PhoneDto dto = getValidPhone(userId);
            PhoneDto createdDto = userClient.createPhone(dto);
            PhoneDto getDto = userClient.getPhone(userId, createdDto.getPhoneId());
            assertEquals(createdDto, getDto);
            } catch(ClientErrorException e) {
                printError(e);
            }
    }

    @Test
    public void testUpdate_whenPhoneNumberUpdated_shouldUpdateSuccessfully() {
        try {
            UUID userId = userClient.create(getValidUser()).getUserId();
            PhoneDto dto = getValidPhone(userId).setPhoneNumber("5675309678");
            PhoneDto createdDto = userClient.createPhone(dto);
            assertEquals("5675309678", createdDto.getPhoneNumber());

            createdDto.setPhoneNumber("8675309678");
            PhoneDto updatedDto = userClient.updatePhone(createdDto);
            assertEquals("8675309678", updatedDto.getPhoneNumber());
        } catch(ClientErrorException e) {
            printError(e);
        }
    }

    @Test
    public void testUpdate_whenInvalid_shouldReturnBadRequest() {
        try{
            UUID userId = userClient.create(getValidUser()).getUserId();
            PhoneDto dto = getValidPhone(userId);
            PhoneDto createdDto = userClient.createPhone(dto);
            createdDto.setPhoneNumber(" ");
            assertThrows(BadRequestException.class, () -> userClient.updatePhone(createdDto));
        } catch(ClientErrorException e) {
            printError(e);
        }
    }

    @Test
    public void testDeleteAndFindAll_whenValid_shouldReturnEmpty() {
        try {
        UUID userId = userClient.create(getValidUser()).getUserId();
        assertEquals(0, userClient.findAllPhones(userId).size());

        PhoneDto dto = getValidPhone(userId);
        PhoneDto createdDto = userClient.createPhone(dto);
        assertEquals(1, userClient.findAllPhones(userId).size());

        userClient.deletePhone(createdDto.getUserId(), createdDto.getPhoneId());
        assertEquals(0, userClient.findAllPhones(userId).size());
        } catch(ClientErrorException e) {
            printError(e);
        }
    }

    private void printError(ClientErrorException e) {
        try (InputStream inputStream = (InputStream) e.getResponse().getEntity()) {
            String message = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            System.err.println(message);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }


    private PhoneDto getValidPhone(UUID userId) {
        return new PhoneDto()
                .setUserId(userId)
                .setPhoneNumber("3067281022")
                .setPhoneType("cell");
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setUsername("doddt")
                .setFirstName("Tim")
                .setLastName("Dodd");
    }


}
