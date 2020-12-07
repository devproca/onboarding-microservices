package ca.devpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;

@SpringBootApplication
public class OnboardingMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnboardingMicroserviceApplication.class, args);
    }
}
