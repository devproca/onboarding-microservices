package ca.devpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;

@SpringBootApplication
public class OnboardingMicroserviceApplication {

    //in intellij preference -> plugins add lombok plugin,
    //also annotation processing must be turned on for all projects that use lombok.

    //finish unit tests for user validator and user controller
    //add validations for length of firstName, lastName username fields
    //add a phone subresource (liquibase, entity, assembler, controller, tests etc)

    public static void main(String[] args) {
        SpringApplication.run(OnboardingMicroserviceApplication.class, args);
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource);
    }
}
