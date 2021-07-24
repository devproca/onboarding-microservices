package ca.devpro.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class ChangenameDto {

    private UUID changenameId;
    private UUID userId;
    private String firstName;
    private String lastName;
    private Timestamp createdDate;

}