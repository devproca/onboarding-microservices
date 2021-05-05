package ca.devpro.controller;

import ca.devpro.api.PhoneDto;
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
public class PhoneControllerTest {

    private UserClient userClient;
    private UserDto testDto;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);
        testDto = userClient.create(getValidUser());
    }

    @Test
    public void testCreate_whenValid_shouldPopulatePhoneId() {
        PhoneDto dto = getValidPhone();
        PhoneDto createdDto = userClient.create(testDto,dto);
        assertNotNull(createdDto.getPhoneId());
    }

    @Test
    public void testCreate_whenInvalid_shouldReturnBadRequest() {
        PhoneDto dto = getValidPhone().setPhoneNumber(" ");

        assertThrows(BadRequestException.class, () -> userClient.create(testDto, dto));
    }

    @Test
    public void testCreateAndGet_whenValid_shouldReturnSameObjects() {
        PhoneDto dto = getValidPhone();
        PhoneDto createdDto = userClient.create(,testDto,dto);
        PhoneDto getDto = userClient.get(testDto.getUserId(),createdDto.getPhoneId());
        assertEquals(createdDto, getDto);
    }

    @Test
    public void testUpdateAndGet_whenValid_shouldReturnSameObjects() {
        PhoneDto dto = getValidPhone();
        PhoneDto createdDto = userClient.create(testDto,dto);
        dto.setPhoneNumber("8675309");
        createdDto = userClient.update(createdDto);
        PhoneDto getDto = userClient.get(testDto.getUserId(),createdDto.getPhoneId());
        assertEquals(createdDto, getDto);
    }

    @Test
    public void testUpdate_whenInvalid_shouldReturnBadRequest() {
        PhoneDto dto = getValidPhone();
        PhoneDto createdDto = userClient.create(testDto,dto);
        createdDto.setPhoneNumber(" ");
        assertThrows(BadRequestException.class, () -> userClient.update(createdDto));
    }

    @Test
    public void testDelete_whenValid_shouldReturnEmpty() {
        PhoneDto dto = getValidPhone();
        PhoneDto createdDto = userClient.create(testDto,dto);
        userClient.delete(createdDto.getPhoneId());
        assertTrue(userClient.findAll().isEmpty());
    }

    private PhoneDto getValidPhone() {
        return new PhoneDto()
                .setPhoneNumber("1234567");
    }

    private UserDto getValidUser() {
        return new UserDto()
                .setUsername("doddt")
                .setFirstName("Tim")
                .setLastName("Dodd");
    }
}
