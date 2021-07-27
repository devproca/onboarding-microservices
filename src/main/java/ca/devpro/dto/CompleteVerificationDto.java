package ca.devpro.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompleteVerificationDto {
    private String code;
}