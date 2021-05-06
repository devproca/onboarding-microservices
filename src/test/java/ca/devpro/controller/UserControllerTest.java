package ca.devpro.controller;

import ca.devpro.api.UserDto;
import ca.devpro.client.UserClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StreamUtils;

import javax.ws.rs.BadRequestException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testUpdateAndGet_whenValid_shouldReturnSameObjects() {
        UserDto dto = getValidUser().setFirstName("dummy");
        UserDto createdDto = userClient.create(dto);
        UserDto updatedDto = userClient.update(createdDto);
        UserDto getDto = userClient.get(updatedDto.getUserId());
        assertEquals(createdDto, getDto);
    }

    @Test
    public void testUpdate_whenInvalid_shouldReturnBadRequest() {

        UserDto dto = getValidUser();
        UserDto createdDto = userClient.create(dto);
        createdDto.setFirstName(" ");
//        UserDto getDto = userClient.get(updatedDto.getUserId());

        UserDto createdDto = userClient.create(getValidUser());
        createdDto.setFirstName("  ");

        assertThrows(BadRequestException.class, () -> userClient.update(createdDto));
    }

    @Test
    public void testDelete_whenValid_shouldReturnEmpty() {
        UserDto dto = getValidUser();
        UserDto createdDto = userClient.create(dto);
        userClient.delete(createdDto.getUserId());
        assertTrue(userClient.findAll().isEmpty());
    }


    private UserDto getValidUser() {
        return new UserDto()
                .setUsername("doddt")
                .setFirstName("Tim")
                .setLastName("Dodd");
    }
}
