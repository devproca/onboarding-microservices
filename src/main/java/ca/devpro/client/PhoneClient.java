package ca.devpro.client;

import ca.devpro.dto.PhoneDto;
import ca.devpro.dto.UserDto;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

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

    private Client client;

    public PhoneClient() {
        client = ClientBuilder.newClient();
    }

    public PhoneDto create(UUID userId, PhoneDto dto) {

        return phoneTarget(userId)
                .request()
                .post(Entity.json(dto), PhoneDto.class);
    }
    public List<PhoneDto> findByUserId(UUID userId) {
        return phoneTarget(userId)
                .request()
                .get(new GenericType<>() {
                });
    }
    public PhoneDto get(UUID phoneId) {
        return phoneTarget(phoneId)
                .request()
                .get(PhoneDto.class);
    }


    public PhoneDto update(PhoneDto dto) {
        System.out.println(dto);
        return phoneTarget(dto.getPhoneId())
                .request()
                .put(Entity.json(dto), PhoneDto.class);
    }



    public void delete(UUID phoneId) {
        phoneTarget(phoneId)
                .request()
                .delete(Void.class);


    }



    private WebTarget phoneTarget(UUID userId) {
        return baseTarget()
                .path("api")
                .path("v1")
                .path("users")
                .path(userId.toString())
                .path("phones");
    }
    private WebTarget baseTarget() {
        return client.target(baseUri);
    }

}

