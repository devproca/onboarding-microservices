package ca.devpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;

@SpringBootApplication
public class OnboardingMicroserviceApplication {

    //finish unit tests for user validator and user controller
    //add validations for length of firstName, lastName, username fields - done
    //add unique username validation (userRepository.existsByUsername)  - done
    //add a phone subresource (liquibase, entity, assembler, controller, tests etc) (look up jpa OneToMany)

    public static void main(String[] args) {
        SpringApplication.run(OnboardingMicroserviceApplication.class, args);
    }
}
