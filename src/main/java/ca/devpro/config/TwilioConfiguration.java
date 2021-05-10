package ca.devpro.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("twilio")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TwilioConfiguration {
    private String accountSid;
    private String authToken;
    private String twilioNumber;
}
