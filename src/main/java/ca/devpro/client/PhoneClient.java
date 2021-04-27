package ca.devpro.client;

import ca.devpro.api.PhoneDto;
import ca.devpro.api.UserDto;
import lombok.Setter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.UUID;

public class PhoneClient {

    @Setter
    private String baseUri;
    private final Client client;

    public PhoneClient() {
        client = ClientBuilder.newClient();
    }

    public PhoneDto create(PhoneDto dto) {
        return phoneTarget(dto.getUserId())
                .request()
                .post(Entity.json(dto), PhoneDto.class);
    }

    public UserDto update(PhoneDto dto) {
        return phoneTarget(dto.getPhoneId())
                .request()
                .put(Entity.json(dto), UserDto.class);
    }

    public PhoneDto get(UUID phoneId) {
        return phoneTarget(phoneId)
                .request()
                .get(PhoneDto.class);
    }

    public List<PhoneDto> findAll(UUID userId) {
        return phoneTarget(userId)
                .request()
                .get(new GenericType<>() {
                });
    }

    public void delete(UUID phoneId) {
        phoneTarget(phoneId)
                .request()
                .delete(Void.class);
    }

    private WebTarget phoneTarget(UUID userId) {
        return client.target(baseUri)
                .path("api")
                .path("v1")
                .path("users")
                .path(userId.toString())
                .path("phones");
    }

    private WebTarget phoneTarget(UUID userId, UUID phoneId) {
        return phoneTarget(userId)
                .path(phoneId.toString());
    }



}
