package ca.devpro.service;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import liquibase.pro.packaged.S;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SmsVerificationService {

    public static final String ACCOUNT_SID = "AC5904e7331581315b698c3943bdc98c9c";
    public static final String AUTH_TOKEN = "02fbd278fe9f92ddc26ea170b2c8d59e";

    public void sendSmsVerificationCode(String verificationCode) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber("+13065357629"),
                new PhoneNumber("+14159493527"),
                verificationCode
        ).create();
    }

    public static String codeGenerator() {
        int maxNum = 1000000;
        Random randomNum = new Random();
        int varificationCode = randomNum.nextInt(maxNum);
        return String.valueOf(varificationCode);
    }
}
