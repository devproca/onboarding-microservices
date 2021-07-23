package ca.devpro.controller;

import ca.devpro.client.PhoneClient;
import ca.devpro.client.UserClient;
import ca.devpro.dto.PhoneDto;
import ca.devpro.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhoneControllerTest {

    @LocalServerPort
    private int port;

    private PhoneClient phoneClient;
    private UserClient userClient;

    @BeforeEach
    public void init() {
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);

        phoneClient = new PhoneClient();
        phoneClient.setBaseUri("http://localhost:" + port);
    }

    @Test
    public void testCreatePhone_whenInvalid_shouldReturnBadRequest() {
        PhoneDto dto = getValidPhone().setPhoneNumber(null);
        assertThrows(BadRequestException.class, () ->  phoneClient.create(dto.getUserId(), dto));
    }

    @Test
    public void testCreatePhone_whenValid_shouldCreatePhone() {
        // Create user first
        UserDto dto = getValidUser();
        UserDto createdDto = userClient.create(dto);

        assertNotNull(createdDto.getUserId());
        PhoneDto phoneDto = getValidPhone();
        PhoneDto createdPhoneDto = phoneClient.create(createdDto.getUserId(), phoneDto);
        assertNotNull(createdPhoneDto.getPhoneId());

    }


    private PhoneDto getValidPhone() {
        return new PhoneDto()
                //.setPhoneId(UUID.fromString("fe0aae1b-a86a-45a2-8104-9b9206ca3a47"))
                .setUserId(UUID.fromString("fe0aae1b-a86a-45a2-8104-9b9206ca3a48"))
                .setPhoneNumber("6472276653");
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testu");
    }
}
