package ca.devpro.api;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class PhoneDto {
    private UUID userId;
    private UUID phoneId;
    private String phoneNumber;
    private String phoneType;
    private Boolean isVerified;
    private String verificationKey;
}
