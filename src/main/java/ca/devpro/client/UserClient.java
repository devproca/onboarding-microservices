package ca.devpro.client;

import ca.devpro.api.ChangeHistoryDto;
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

    public PhoneDto createPhone(PhoneDto dto) {
        return phoneTarget(dto.getUserId())
                .request()
                .post(Entity.json(dto), PhoneDto.class);
    }

    public PhoneDto getPhone(UUID userId, UUID phoneId) {
        return phoneTarget(userId, phoneId)
                .request()
                .get(PhoneDto.class);
    }

    public List<PhoneDto> findAllPhones(UUID userId) {
        return phoneTarget(userId)
                .request()
                .get(new GenericType<>(){});
    }

    public PhoneDto updatePhone(PhoneDto dto) {
        return phoneTarget(dto.getUserId(), dto.getPhoneId())
                .request()
                .put(Entity.json(dto), PhoneDto.class);
    }

    public void deletePhone(UUID userId, UUID phoneId) {
        phoneTarget(userId, phoneId)
                .request()
                .delete(Void.class);
    }

    public ChangeHistoryDto createChangeHistory(ChangeHistoryDto dto) {
        return changeHistoryTarget(dto.getUserId())
                .request()
                .post(Entity.json(dto), ChangeHistoryDto.class);
    }

    public ChangeHistoryDto getNameChange(UUID userId, UUID versionId) {
        return changeHistoryTarget(userId, versionId)
                .request()
                .get(ChangeHistoryDto.class);
    }

    public List<ChangeHistoryDto> findAllNameChanges(UUID userId) {
        return changeHistoryTarget(userId)
                .request()
                .get(new GenericType<>(){});
    }


    private WebTarget phoneTarget(UUID userId, UUID phoneId){
        return phoneTarget(userId)
                .path(phoneId.toString());
    }

    private WebTarget phoneTarget(UUID userId) {
        return userTarget(userId)
                .path("phones");
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

    private WebTarget changeHistoryTarget(UUID userId, UUID versionId) {
        return userTarget(userId)
                .path(versionId.toString());
    }

    private WebTarget changeHistoryTarget(UUID userId) {
        return userTarget(userId)
                .path("changehistory");
    }

    private WebTarget baseTarget() {
        return client.target(baseUri);
    }
}
