package ca.devpro.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)

public class NameChangeDto {
    private UUID userId;
    private UUID nameChangeId;
    private String previousUsername;
    private String updatedUserName;
    private String previousFirstName;
    private String updatedFirstName;
    private String previousLastName;
    private String updatedLastName;
}
