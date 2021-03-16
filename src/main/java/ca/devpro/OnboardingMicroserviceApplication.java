package ca.devpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;

@SpringBootApplication
public class OnboardingMicroserviceApplication {

    // In intellij preference -> plugins add lombok plugin. COMPLETED
    // Also annotation processing must be turned on for all projects that use lombok. COMPLETED
    // Finish unit tests for user validator and user controller. COMPLETE
    // Add validations for length of firstName, lastName, and username fields. COMPLETE
    // Add unique username validation (userRepository.existsByUsername). COMPLETE
    // Add a phone subresource (liquibase, entity, assembler, controller, tests etc) (look up jpa OneToMany). COMPLETE

    public static void main(String[] args) {
        SpringApplication.run(OnboardingMicroserviceApplication.class, args);
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource);
    }
}
