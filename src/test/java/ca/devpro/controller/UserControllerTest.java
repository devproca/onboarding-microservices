package ca.devpro.controller;

import ca.devpro.api.UserClient;
import ca.devpro.api.UserDto;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    private UserClient userClient;

    @BeforeEach
    public void init() {
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);
    }

    @Test
    public void testCreate_whenValid_shouldCreateUser() {
        UserDto dto = getValidUser();
        UserDto createdDto = userClient.create(dto);
        assertNotNull(createdDto.getUserId());
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testuser");
    }
}
