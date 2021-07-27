package ca.devpro;

import ca.devpro.dto.TwilioConfigDTO;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);
    private final TwilioConfigDTO twilioConfigDTO;

    @Autowired
    public TwilioInitializer(TwilioConfigDTO twilioConfigDTO){
        this.twilioConfigDTO = twilioConfigDTO;
        Twilio.init(
                twilioConfigDTO.getAccountSid(),
                twilioConfigDTO.getAuthToken()
        );
        LOGGER.info("Twilio initialized .. with account sid {} ", twilioConfigDTO.getAccountSid());
    }

}
