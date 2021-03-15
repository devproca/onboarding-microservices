package ca.devpro.controller;

import ca.devpro.api.UserDto;
import ca.devpro.client.UserClient;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import javax.ws.rs.NotFoundException;

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

    // Create Tests
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

    @Test
    public void testCreateAndUpdate_shouldCreateAndUpdateSuccessfully() { // Update Test
        UserDto dto = newValidUserForCreate();
        UserDto createdDto = userClient.create(dto);

        assertNotNull(createdDto.getUserId());
        createdDto.setFirstName("updateTest");
        assertEquals(createdDto.getFirstName(), "updateTest");
    }

    @Test
    public void testCreateAndDelete_shouldCreateAndDeleteUserSuccessfully() { // Delete Test
        UserDto dto = newValidUserForCreate();
        UserDto createdDto = userClient.create(dto);

        assertNotNull(createdDto.getUserId());
        userClient.delete(createdDto.getUserId());
        assertThrows(NotFoundException.class, () -> userClient.get(createdDto.getUserId()));
    }

    // Find User(s) Tests
    @Test
    public void testFindSingleUser_shouldCreateAndFindUserSuccessfully() {
        UserDto dto = newValidUserForCreate();
        UserDto createdDto = userClient.create(dto);

        assertNotNull(userClient.get(createdDto.getUserId()));
    }

    @Test
    public void testFindAllUsers_shouldCreateAndFindAllUsersSuccessfully() {
        UserDto dto = newValidUserForCreate();
        UserDto createdDtoOne = userClient.create(dto);
        UserDto createdDtoTwo = userClient.create(dto.setUsername("Test the Second"));
        UserDto createdDtoThree = userClient.create(dto.setUsername("Test the Third"));

        assertEquals(3, userClient.findAll().size());
    }

    @Test
    public void testFindAll_afterDeletion_shouldCreateThenDeleteAndFindAllUsersSuccessfully() {
        UserDto dto = newValidUserForCreate();
        UserDto createdDtoOne = userClient.create(dto);
        UserDto createdDtoTwo = userClient.create(dto.setUsername("Test the Second"));
        UserDto createdDtoThree = userClient.create(dto.setUsername("Test the Third"));

        assertEquals(3, userClient.findAll().size());
        userClient.delete(createdDtoTwo.getUserId());
        assertThrows(NotFoundException.class, () -> userClient.get(createdDtoTwo.getUserId()));
        assertEquals(2, userClient.findAll().size());
    }


    private UserDto newValidUserForCreate() { // Test Data
        return new UserDto()
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testuser");
    }
}
