package ca.devpro.controller;

import ca.devpro.api.UserDto;
import ca.devpro.client.UserClient;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    public void testCreate_whenValidUser_shouldCreateSuccessfully() {
        UserDto dto = newValidUserForCreate();
        UserDto createdDto = userClient.create(dto);
        assertNotNull(createdDto.getUserId());
    }

    @Test
    public void testCreateAndGet_shouldReturnSameResponseBody() {
        UserDto dto = newValidUserForCreate();
        UserDto createdDto = userClient.create(dto);
        UserDto getDto = userClient.get(createdDto.getUserId());
        assertEquals(createdDto, getDto);
    }

    private UserDto newValidUserForCreate() {
        return new UserDto()
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testuser");
    }
}
