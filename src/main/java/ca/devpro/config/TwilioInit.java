package ca.devpro.config;

import com.twilio.Twilio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInit {
    private final TwilioConfig twilioConfig;
    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInit.class);

    @Autowired
    public TwilioInit(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());

        LOGGER.info("Twilio initialized ... with Account SID: {} ", twilioConfig.getAccountSid());
        LOGGER.info("Twilio initialized ... with Sender Phone Number: {} ", twilioConfig.getTwilioNumber());
    }
}
