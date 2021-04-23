package ca.devpro.controller;

import ca.devpro.api.UserDto;
import ca.devpro.client.UserClient;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import javax.ws.rs.BadRequestException;

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
    public void testCreate_whenValidUser_shouldCreateUser() {
        UserDto dto = getValidUser();
        UserDto createdDto = userClient.create(dto);
        assertNotNull(createdDto.getUserId());
    }

    @Test
    public void testCreateAndGet_shouldReturnSameResponse() {
        UserDto dto = getValidUser();
        UserDto createdDto = userClient.create(dto);
        UserDto getDto = userClient.get(createdDto.getUserId());
        assertEquals(createdDto, getDto);
    }

    @Test
    public void testCreate_whenInvalidUser_shouldGenerateBadRequest() {
        UserDto dto = getValidUser().setFirstName(null);
        assertThrows(BadRequestException.class, () -> {
            userClient.create(dto);
        });
    }

    @Test
    public void testUpdate_shouldReturnDtoWithDifferentUserID_ShouldPass() {
        UserDto dto = getValidUser();
        UserDto updatedDto = userClient.update(dto);
        assertNotEquals(dto, updatedDto);
    }


    private UserDto getValidUser() {
        return new UserDto()
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testuser");
    }
}
