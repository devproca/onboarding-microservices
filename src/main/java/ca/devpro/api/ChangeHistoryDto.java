package ca.devpro.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class ChangeHistoryDto {
    private UUID userId;
    private UUID versionId;
    private String previousUsername;
    private String updatedUsername;
    private String previousFirstName;
    private String updatedFirstName;
    private String previousLastName;
    private String updatedLastName;
    private Timestamp timeStamp;
}