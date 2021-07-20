package ca.devpro.controller;

import ca.devpro.client.UserClient;
import ca.devpro.dto.UserDto;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    public void testCreate_whenInvalid_shouldReturnBadRequest() {
        UserDto dto = getValidUser().setFirstName(null);
        assertThrows(BadRequestException.class, () ->  userClient.create(dto));
    }

    @Test
    public void testCreate_whenValid_shouldCreateUser() {
        UserDto dto = getValidUser();
        UserDto createdDto = userClient.create(dto);
        assertNotNull(createdDto.getUserId());
        // Time to delete -  for reuse purposes
        userClient.delete(createdDto.getUserId());
        assertThrows(NotFoundException.class, () -> userClient.get(createdDto.getUserId()));
    }

    @Test
    public void testDelete_shouldDeleteSuccessfully() { // Delete Test
        // Should create new record for delete first similar to testCreate_whenValid_shouldCreateUser
        UserDto dto = getValidUser();
        UserDto newDto = userClient.create(dto);
        assertNotNull(newDto.getUserId());
        // Time to delete -  for reuse purposes
        userClient.delete(newDto.getUserId());
        assertThrows(NotFoundException.class, () -> userClient.get(newDto.getUserId()));
    }

    @Test
    public void testUpdate_shouldUpdateSuccessfully() { // Update Test
        // Should create new record for delete first similar to testCreate_whenValid_shouldCreateUser
        UserDto dto = getValidUser();
        UserDto newDto = userClient.create(dto);
        assertNotNull(newDto.getUserId());
        // Time to update
        newDto.setLastName("newL");
        userClient.update(newDto);
        assertEquals(newDto.getLastName(), "newL");
        // Time to delete -  for reuse purposes
        userClient.delete(newDto.getUserId());
        assertThrows(NotFoundException.class, () -> userClient.get(newDto.getUserId()));
    }

    @Test
    public void testOneUser_shouldFindOneUserSuccessfully() {
        UserDto newDto = userClient.create(getValidUser().setUsername("123").setLastName("99999").setFirstName("99999"));
        // should not return a null
        assertNotNull(userClient.get(newDto.getUserId()));
        // Time to delete -  for reuse purposes
        userClient.delete(newDto.getUserId());
        assertThrows(NotFoundException.class, () -> userClient.get(newDto.getUserId()));
    }

    @Test
    public void testFindAll_shouldFindAllSuccessfully() {

        UserDto dto = getValidUser();
        UserDto oneRec1 = userClient.create(dto);
        UserDto oneRec2 = userClient.create(dto.setFirstName("12345").setLastName("12345").setUsername("66666"));
        UserDto oneRec3 = userClient.create(dto.setFirstName("54321").setLastName("54321").setUsername("77777"));
        UserDto oneRec4 = userClient.create(dto.setFirstName("11111").setLastName("11111").setUsername("11111"));
        UserDto oneRec5 = userClient.create(dto.setFirstName("22222").setLastName("22222").setUsername("22222"));
        assertEquals(5, userClient.findAll().size());
        // Time to delete
        userClient.delete(oneRec1.getUserId());
        assertThrows(NotFoundException.class, () -> userClient.get(oneRec1.getUserId()));
        userClient.delete(oneRec2.getUserId());
        assertThrows(NotFoundException.class, () -> userClient.get(oneRec2.getUserId()));
        userClient.delete(oneRec3.getUserId());
        assertThrows(NotFoundException.class, () -> userClient.get(oneRec3.getUserId()));
        userClient.delete(oneRec4.getUserId());
        assertThrows(NotFoundException.class, () -> userClient.get(oneRec4.getUserId()));
        userClient.delete(oneRec5.getUserId());
        assertThrows(NotFoundException.class, () -> userClient.get(oneRec5.getUserId()));

    }

    private UserDto getValidUser() {
        return new UserDto()
                .setFirstName("test")
                .setLastName("user")
                .setUsername("testu");
    }
}
