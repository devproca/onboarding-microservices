package ca.devpro.controller;
import ca.devpro.api.PhoneDto;
import ca.devpro.api.UserDto;
import ca.devpro.client.PhoneClient;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import ca.devpro.client.UserClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import javax.ws.rs.BadRequestException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PhoneControllerTest {

    @LocalServerPort
    private int port;

    private UserClient userClient;
    private PhoneClient phoneClient;

    @BeforeEach
    public void init() {
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);
        phoneClient = new PhoneClient();
        phoneClient.setBaseUri("http://localhost:" + port);
    }

    @Test
    public void testCreate_whenValidPhone_shouldCreatePhone() {
        PhoneDto dto = getValidPhone();
        PhoneDto createdDto = phoneClient.create(dto);
        assertNotNull(createdDto.getPhoneId());
    }

    private PhoneDto getValidPhone() {
        return new PhoneDto()
                //.setUserId()
                .setPhoneNumber("test")
                .setPhoneType("phone");
    }
}
