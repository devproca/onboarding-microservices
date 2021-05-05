package ca.devpro.client;

import ca.devpro.api.UserDto;
import lombok.Setter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.UUID;

public class UserClient {

    private Client client;

    @Setter
    private String baseUri;

    public UserClient() {
        client = ClientBuilder.newClient();
    }

    public UserDto create(UserDto dto) {
        return userTarget()
                .request()
                .post(Entity.json(dto), UserDto.class);
    }

    public UserDto get(UUID userId) {
        return userTarget(userId)
                .request()
                .get(UserDto.class);
    }

    public List<UserDto> findAll() {
        return userTarget()
                .request()
                .get(new GenericType<>(){});
    }

    public UserDto update(UserDto dto) {
        return userTarget(dto.getUserId())
                .request()
                .put(Entity.json(dto), UserDto.class);
    }

    public void delete(UUID userId) {
        userTarget(userId)
                .request()
                .delete(Void.class);
    }

    private WebTarget userTarget(UUID userId) {
        return userTarget()
                .path(userId.toString());
    }



    private WebTarget userTarget() {
        return baseTarget()
                .path("api")
                .path("v1")
                .path("users");

    }

    private WebTarget phoneTarget(UUID userId, UUID phoneId) {
        return baseTarget()
                .path(userId.toString())
                .path("phones")
                .path(phoneId.toString());
    }

    private WebTarget baseTarget() {
        return client.target(baseUri);
    }
}
