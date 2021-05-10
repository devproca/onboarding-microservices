package ca.devpro.service;

import ca.devpro.config.TwilioConfiguration;
import ca.devpro.entity.Phone;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class VerificationService {

    private TwilioConfiguration twilioConfiguration;

    @Autowired
    public VerificationService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    public void initiateVerification(String phoneNumber, String verificationKey) {
        Phone from = new Phone();
        from.setPhoneNumber(twilioConfiguration.getTwilioNumber());
        Phone to = new Phone();


        MessageCreator creator = Message.creator(to, from, "Hi").create();
    }

}
