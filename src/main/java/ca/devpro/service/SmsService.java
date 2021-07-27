package ca.devpro.service;

import ca.devpro.TwilioSmsSender;
import ca.devpro.dto.SmsRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SmsService {

    private final SmsSender smsSender;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender twilioSmsSender) {
        this.smsSender = twilioSmsSender;
    }

    public void sendSms(SmsRequestDto smsRequestDto){
        smsSender.sendSms(smsRequestDto);
    }

    public static String codeVerifyGenerator() {
        Random random = new Random();
        int code = random.nextInt(100000);
        return String.format("%06d", code);
    }

}
