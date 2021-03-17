package ca.devpro.service;

import ca.devpro.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VerificationService {

    private TwilioConfig twilioConfig;

    @Autowired
    public VerificationService(TwilioConfig twilioConfig) { this.twilioConfig = twilioConfig; }

    public void sendVerifyCode(String phoneNumber, String verifyCode) {
        PhoneNumber recipient = new PhoneNumber(phoneNumber);
        PhoneNumber twilioNumber = new PhoneNumber(twilioConfig.getTwilioNumber());

        Message.creator(recipient, twilioNumber, "Verification Code: " + verifyCode).create();
    }

    public static String generateVerifyCode() {
        Random r = new Random();
        int code = r.nextInt(100000);

        return String.format("%06d", code);
    }
}
