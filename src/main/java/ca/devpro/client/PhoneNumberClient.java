package ca.devpro.client;

import ca.devpro.api.PhoneNumberDto;
import ca.devpro.api.VerificationDto;
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

    public PhoneNumberDto create(PhoneNumberDto dto) {
        return phoneTarget(dto.getUserId())
                .request()
                .post(Entity.json(dto), PhoneNumberDto.class);
    }

    public PhoneNumberDto update(PhoneNumberDto dto) {
        return phoneTarget(dto.getUserId(), dto.getPhoneId())
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

    public void sendVerificationCode(UUID userId, UUID phoneId) {
        phoneCodeTarget(userId, phoneId)
                .request()
                .post(Entity.json(null), VerificationDto.class);
    }

    public PhoneNumberDto verifyCode(PhoneNumberDto phoneDto, VerificationDto verifyDto) {
        phoneVerificationTarget(phoneDto.getUserId(), phoneDto.getPhoneId())
                .request()
                .post(Entity.json(verifyDto), VerificationDto.class);

        return phoneTarget(phoneDto.getUserId(), phoneDto.getPhoneId())
                .request()
                .get(PhoneNumberDto.class);
    }

    private WebTarget phoneTarget(@NonNull UUID userId) {
        return baseTarget()
                .path("api")
                .path("v1")
                .path("users")
                .path(userId.toString())
                .path("phonenumbers");
    }

    private WebTarget phoneTarget(@NonNull UUID userId, @NonNull UUID phoneId) {
        return phoneTarget(userId)
                .path(phoneId.toString());
    }

    private WebTarget phoneCodeTarget(@NonNull UUID userId, @NonNull UUID phoneId) {
        return phoneTarget(userId)
                .path(phoneId.toString())
                .path("initiateVerification");
    }

    private WebTarget phoneVerificationTarget(@NonNull UUID userId, @NonNull UUID phoneId) {
        return phoneTarget(userId)
                .path(phoneId.toString())
                .path("verify");
    }

    private WebTarget baseTarget() {
        return client.target(baseUri);
    }
}
