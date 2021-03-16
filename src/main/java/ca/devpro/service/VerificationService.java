package ca.devpro.service;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VerificationService {
    public static final String AUTH_TOKEN = "858c4e1255e47c24de98ba59a93c6399"; // ENV variable these.
    public static final String ACCOUNT_SID = "ACb907c199d7730e6e0f6da0a2f38a1ce0";

    public void sendVerifyCode(String phoneNumber, String verifyCode) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        PhoneNumber recipient = new PhoneNumber(phoneNumber);
        PhoneNumber twilioNumber = new PhoneNumber("+16029752728");

        Message.creator(recipient, twilioNumber, "Verification Code: " + verifyCode).create();
    }

    public static String generateVerifyCode() {
        Random r = new Random();
        int code = r.nextInt(100000);

        return String.format("%06d", code);
    }
}
