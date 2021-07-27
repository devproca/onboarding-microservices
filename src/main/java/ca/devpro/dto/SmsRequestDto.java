package ca.devpro.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SmsRequestDto {

    private String phoneNumber;
    private String message;

    @Override
    public String toString() {
        return "SmsRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
