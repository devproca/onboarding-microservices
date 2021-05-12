package ca.devpro.service;

import ca.devpro.api.NameChangeDto;
import ca.devpro.api.UserDto;
import ca.devpro.repository.NameChangeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ca.devpro.client.UserClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class NameChangeValidatorTest {

    @LocalServerPort
    private int port;

    private NameChangeRepository nameChangeRepository;
    private UserClient userClient;

    @BeforeEach
    public void init() {
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);
        nameChangeRepository = mock(NameChangeRepository.class);
    }

    @Test
    public void testValidate_WhenNoChange_ShouldReturnError(){

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

    private NameChangeDto getInvalidNameChange(UserDto dto) {
        NameChangeDto nameChangeDto = new NameChangeDto();
        nameChangeDto.setUserId(dto.getUserId());
        nameChangeDto.setPreviousUsername(dto.getUsername());
        nameChangeDto.setUpdatedUsername(dto.getUsername());
        nameChangeDto.setPreviousFirstName(dto.getFirstName());
        nameChangeDto.setUpdatedFirstName(dto.getFirstName());
        nameChangeDto.setPreviousLastName(dto.getLastName());
        nameChangeDto.setUpdatedLastName(dto.getLastName());

        return nameChangeDto;
    }
}
