package ca.devpro;

import ca.devpro.dto.SmsRequestDto;
import ca.devpro.dto.TwilioConfigDTO;
import ca.devpro.service.SmsSender;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);
    private final TwilioConfigDTO twilioConfigDTO;

    @Autowired
    public TwilioSmsSender(TwilioConfigDTO twilioConfigDTO) {
        this.twilioConfigDTO = twilioConfigDTO;
    }

    @Override
    public void sendSms(SmsRequestDto smsRequestDto) {
        if(isPhoneNumberValid(smsRequestDto.getPhoneNumber())){
            PhoneNumber to = new PhoneNumber(smsRequestDto.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfigDTO.getTrialNumber());
            String message = smsRequestDto.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            LOGGER.info("Send sms {}" + smsRequestDto);

        } else {
            throw new IllegalArgumentException(
                    "Phone number " + smsRequestDto.getPhoneNumber() + " is not a valid number");
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber){
        if(phoneNumber.length() > 12){
            return false;
        } else {
            return true;
        }
    }

}
