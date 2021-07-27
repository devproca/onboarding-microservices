package ca.devpro.service;

import ca.devpro.dto.SmsRequestDto;

public interface SmsSender {
    void sendSms(SmsRequestDto smsRequestDto);
}
