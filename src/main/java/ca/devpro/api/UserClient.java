package ca.devpro.api;

import lombok.Setter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.UUID;

public class UserClient {

    private final Client client;

    @Setter
    private String baseUri;

    public UserClient() {
        client = ClientBuilder.newClient();
    }

    public UserDto create(UserDto dto) {
        return usersTarget()
                .request()
                .post(Entity.json(dto), UserDto.class);
    }

    public UserDto get(UUID userId) {
        return usersTarget(userId)
                .request()
                .get(UserDto.class);
    }

    public Response delete(UUID userId) {
        return usersTarget(userId)
                .request()
                .delete();
    }

    private WebTarget usersTarget(UUID userId) {
        return usersTarget()
                .path(userId.toString());
    }

    private WebTarget usersTarget() {
        return baseTarget()
                .path("api")
                .path("v1")
                .path("users");
    }

    private WebTarget baseTarget() {
        return client.target(baseUri);
    }

}
