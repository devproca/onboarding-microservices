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

    private UserClient userClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);
    }

    @Test
    public void testCreate_whenValid_shouldPopulateUserId() {
        UserDto dto = getValidUser();
        UserDto createdDto = userClient.create(dto);
        assertNotNull(createdDto.getUserId());
    }

    @Test
    public void testCreate_whenInvalid_shouldReturnBadRequest() {
        UserDto dto = getValidUser().setFirstName(" ");

        assertThrows(BadRequestException.class, () -> userClient.create(dto));
    }

    @Test
    public void testCreateAndGet_whenValid_shouldReturnSameObjects() {
        UserDto dto = getValidUser();
        UserDto createdDto = userClient.create(dto);
        UserDto getDto = userClient.get(createdDto.getUserId());
        assertEquals(createdDto, getDto);
    }

    @Test
    public void testCreateAndGet_whenInValid_shouldReturnBadRequest() {
        UserDto dto = getValidUser().setFirstName(" ");
        assertThrows(BadRequestException.class, () -> userClient.create(dto));
        UserDto createdDto = userClient.create(dto);
        assertThrows(BadRequestException.class, () -> userClient.get(createdDto.getUserId()));

    }
    @Test
    public void testUpdate_whenValid_shouldPopulateUserId() {
        UserDto dto = getValidUser();
        UserDto updatedDto = userClient.update(dto);
        assertNotNull(updatedDto.getUserId());
    }

    @Test
    public void testUpdate_whenInvalid_shouldReturnBadRequest() {
        UserDto dto = getValidUser().setFirstName(" ");
        assertThrows(BadRequestException.class, () -> userClient.update(dto));
    }

    @Test
    public void testDelete_whenValid_shouldPopulateUserId() {
        UserDto deletedDto = userClient.delete(dto);
        UserDto deleteDto = userClient.get(deletedDto.getUserId());
        assertNotNull(deleteDto);

    }

    @Test
    public void testDelete_whenInvalid_shouldReturnBadRequest() {
        UserDto deletedDto = userClient.create(dto);
        assertThrows(BadRequestException.class, () -> userClient.delete(deletedDto.getUserId()));

    }

    private UserDto getValidUser() {
        return new UserDto()
                .setUsername("doddt")
                .setFirstName("Tim")
                .setLastName("Dodd");
    }
}
