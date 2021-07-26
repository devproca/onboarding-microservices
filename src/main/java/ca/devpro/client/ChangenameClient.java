package ca.devpro.client;

import ca.devpro.dto.ChangenameDto;
import lombok.Setter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.util.UUID;

public class ChangenameClient {
    @Setter
    private String baseUri;

    private Client client;

    public ChangenameClient() {
        client = ClientBuilder.newClient();
    }

    public ChangenameDto create(UUID userId, ChangenameDto dto) {
        return changenameTarget(userId)
                .request()
                .post(Entity.json(dto), ChangenameDto.class);
    }

    private WebTarget changenameTarget(UUID userId) {
        return baseTarget()
                .path("api")
                .path("v1")
                .path("users");
    }

    private WebTarget baseTarget() {
        return client.target(baseUri);
    }

}
