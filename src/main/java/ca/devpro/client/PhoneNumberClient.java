package ca.devpro.client;

import ca.devpro.api.PhoneNumberDto;
import lombok.NonNull;
import lombok.Setter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.UUID;

public class PhoneNumberClient {

    @Setter
    private String baseUri;

    private Client client;

    public PhoneNumberClient() { client  = ClientBuilder.newClient(); }

    public PhoneNumberDto create(UUID userId, PhoneNumberDto dto) {
        return phoneTarget(userId)
                .request()
                .post(Entity.json(dto), PhoneNumberDto.class);
    }

    public PhoneNumberDto update(UUID userId, PhoneNumberDto dto) {
        return phoneTarget(dto.getPhoneId())
                .request()
                .put(Entity.json(dto), PhoneNumberDto.class);
    }

    public PhoneNumberDto get(UUID userId, UUID phoneId) {
        return phoneTarget(userId, phoneId)
                .request()
                .get(PhoneNumberDto.class);
    }

    public void delete(UUID userId, UUID phoneId) {
        phoneTarget(userId, phoneId)
                .request()
                .delete(Void.class);
    }

    public List<PhoneNumberDto> findAll(UUID userId) {
        return phoneTarget(userId)
                .request()
                .get(new GenericType<>() {
                });
    }

    public WebTarget phoneTarget(@NonNull UUID userId) {
        return baseTarget()
                .path("api")
                .path("v1")
                .path("users")
                .path(userId.toString())
                .path("phonenumbers");
    }

    public WebTarget phoneTarget(@NonNull UUID userId, @NonNull UUID phoneId) {
        return phoneTarget(userId)
                .path(phoneId.toString());
    }

    public WebTarget baseTarget() {
        return client.target(baseUri);
    }
}
