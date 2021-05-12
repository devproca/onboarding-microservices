package ca.devpro.service;

import ca.devpro.config.TwilioConfiguration;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@AllArgsConstructor
@Slf4j
public class SmsService {

    private final TwilioConfiguration twilioConfiguration;

    @PostConstruct
    public void init() {
        Twilio.init(
                twilioConfiguration.getAccountSid(),
                twilioConfiguration.getAuthToken()
        );
        log.info("Twilio initialized ... with account sid: {} ", twilioConfiguration.getAccountSid());
        log.info("Twilio initialized ... with phone number: {} ", twilioConfiguration.getTwilioNumber());
    }

    public void sendSmsMessage(String phoneNumber, String body) {
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTwilioNumber());
        PhoneNumber to = new PhoneNumber("+1" + phoneNumber);
        MessageCreator creator = Message.creator(to, from, body);
        creator.create();
    }
}
