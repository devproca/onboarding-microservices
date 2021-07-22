package ca.devpro.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class PhoneDto {

    private UUID phoneId;
    private String userId;
    private String phoneNumber;

}
