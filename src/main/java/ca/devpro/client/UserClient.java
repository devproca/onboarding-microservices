package ca.devpro.client;

import ca.devpro.api.UserDto;
import lombok.NonNull;
import lombok.Setter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.UUID;

public class UserClient {

    @Setter
    private String baseUri;

    private Client client;

    public UserClient() {
        client = ClientBuilder.newClient();
    }

    public UserDto create(UserDto dto) {
        return userTarget()
                .request()
                .post(Entity.json(dto), UserDto.class);
    }

    public UserDto update(UserDto dto) {
        return userTarget(dto.getUserId())
                .request()
                .put(Entity.json(dto), UserDto.class);
    }

    public UserDto get(UUID userId) {
        return userTarget(userId)
                .request()
                .get(UserDto.class);
    }

    public void delete(UUID userId) {
        userTarget(userId)
                .request()
                .delete(Void.class);
    }

    public List<UserDto> findAll() {
        return userTarget()
                .request()
                .get(new GenericType<>() {
                });
    }

    private WebTarget userTarget(@NonNull UUID userId) {
        return userTarget()
                .path(userId.toString());
    }

    private WebTarget userTarget() {
        return baseTarget()
                .path("api")
                .path("v1")
                .path("users");
    }

    private WebTarget baseTarget() {
        return client.target(baseUri);
    }
}
