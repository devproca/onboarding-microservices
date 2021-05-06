package ca.devpro.controller;

import ca.devpro.api.PhoneDto;
import ca.devpro.api.UserDto;
import ca.devpro.client.UserClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import javax.ws.rs.BadRequestException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PhoneControllerTest {

    private UserClient userClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);
    }

    @Test
    public void testCreate_whenValid_shouldPopulatePhoneId() {
        UserDto userDto = userClient.create(getValidUser());
        PhoneDto dto = getValidPhone(userDto.getUserId());
        PhoneDto createdDto = userClient.createPhone(dto);
        assertNotNull(createdDto.getPhoneId());
    }

    @Test
    public void testCreate_whenInvalid_shouldReturnBadRequest() {
        PhoneDto dto = getValidPhone(UUID.randomUUID()).setPhoneNumber(" ");
        assertThrows(BadRequestException.class, () -> userClient.createPhone(dto));
    }

    @Test
    public void testCreateAndGet_whenValid_shouldReturnSameObjects() {
        UUID userId = userClient.create(getValidUser()).getUserId();
        PhoneDto dto = getValidPhone(userId);
        PhoneDto createdDto = userClient.createPhone(dto);
        PhoneDto getDto = userClient.getPhone(userId, createdDto.getPhoneId());
        assertEquals(createdDto, getDto);
    }

    @Test
    public void testUpdate_whenPhoneNumberUpdated_shouldUpdateSuccessfully() {
        UUID userId = userClient.create(getValidUser()).getUserId();
        PhoneDto dto = getValidPhone(userId).setPhoneNumber("5675309");
        PhoneDto createdDto = userClient.createPhone(dto);
        assertEquals("5675309", createdDto.getPhoneNumber());

        createdDto.setPhoneNumber("8675309");
        PhoneDto updatedDto = userClient.updatePhone(createdDto);
        assertEquals("8675309", updatedDto.getPhoneNumber());
    }

    @Test
    public void testUpdate_whenInvalid_shouldReturnBadRequest() {
        UUID userId = userClient.create(getValidUser()).getUserId();
        PhoneDto dto = getValidPhone(userId);
        PhoneDto createdDto = userClient.createPhone(dto);
        createdDto.setPhoneNumber(" ");
        assertThrows(BadRequestException.class, () -> userClient.updatePhone(createdDto));
    }

    @Test
    public void testDeleteAndFindAll_whenValid_shouldReturnEmpty() {
        UUID userId = userClient.create(getValidUser()).getUserId();
        assertEquals(0, userClient.findAllPhones(userId).size());

        PhoneDto dto = getValidPhone(userId);
        PhoneDto createdDto = userClient.createPhone(dto);
        assertEquals(1, userClient.findAllPhones(userId).size());

        userClient.deletePhone(createdDto.getUserId(), createdDto.getPhoneId());
        assertEquals(0, userClient.findAllPhones(userId).size());
    }


    private PhoneDto getValidPhone(UUID userId) {
        return new PhoneDto()
                .setUserId(userId)
                .setPhoneNumber("3067281022");
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setUsername("doddt")
                .setFirstName("Tim")
                .setLastName("Dodd");
    }



}
