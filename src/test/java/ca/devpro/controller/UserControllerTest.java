package ca.devpro.controller;

import ca.devpro.api.UserDto;
import ca.devpro.client.UserClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private UserClient userClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);
    }

    @Test
    public void testCreate_whenValidUser_shouldCreateUser() {
        UserDto dto = getValidUser();
        UserDto createdDto = userClient.create(dto);
        assertNotNull(createdDto.getUserId());
    }

    @Test
    public void testCreateAndUpdate_whenUsernameUpdated_shouldNotUpdateUsername() {
        UserDto dto = getValidUser()
                .setUsername("initialusername")
                .setFirstName("initialfirstname");

        UserDto createdDto = userClient.create(dto);
        createdDto.setUsername("updatedusername")
                    .setFirstName("updatedfirstname");

        UserDto updatedDto = userClient.update(createdDto);
        assertEquals("initialusername", updatedDto.getUsername());
        assertEquals("updatedfirstname", updatedDto.getFirstName());
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setLastName("testlast")
                .setFirstName("testfirst")
                .setUsername("testuser");
    }

}
