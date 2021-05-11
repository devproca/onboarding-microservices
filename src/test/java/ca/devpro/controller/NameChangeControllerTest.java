package ca.devpro.controller;

import ca.devpro.api.NameChangeDto;
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

public class NameChangeControllerTest {
    //create name change
    //get list of users name changes

    private UserClient userClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);
    }

    @Test
    public void testCreate_whenValid_shouldPopulateNameChangeId() {
        UserDto user = createUser();
        user.setFirstName("test");
        NameChangeDto dto = populateNameChange(user);
        NameChangeDto createdDto = userClient.updateName(dto);
        assertNotNull(createdDto.getNameChangeId());
    }

    @Test
    public void testCreate_whenInvalid_shouldReturnBadRequest() {
        UserDto user = createUser();
        user.setFirstName("");
        NameChangeDto dto = populateNameChange(user);
        assertThrows(BadRequestException.class, () -> userClient.updateName(dto));
    }

    @Test
    public void testCreateAndGet_whenValid_shouldReturnSameObjects() {
        UserDto user = createUser();
        user.setFirstName("test");
        NameChangeDto dto = populateNameChange(user);
        NameChangeDto createdDto = userClient.updateName(dto);
        assertEquals(dto, createdDto);
    }

    private UserDto createUser() {
        return userClient.create(getValidUser());
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setUsername("doddt")
                .setFirstName("Tim")
                .setLastName("Dodd");
    }

    private NameChangeDto populateNameChange(UserDto dto) {
        NameChangeDto nameChangeDto = new NameChangeDto();

        UserDto previousDto = userClient.get(dto.getUserId());
        nameChangeDto.setUserId(dto.getUserId());
        nameChangeDto.setPreviousUsername(previousDto.getUsername());
        nameChangeDto.setUpdatedUsername(dto.getUsername());
        nameChangeDto.setPreviousFirstName(previousDto.getFirstName());
        nameChangeDto.setUpdatedFirstName(dto.getFirstName());
        nameChangeDto.setPreviousLastName(previousDto.getLastName());
        nameChangeDto.setUpdatedLastName(dto.getLastName());

        return nameChangeDto;
    }
}
