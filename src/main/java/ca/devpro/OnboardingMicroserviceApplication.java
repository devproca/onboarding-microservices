package ca.devpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;

@SpringBootApplication
public class OnboardingMicroserviceApplication {

    //finish unit tests for user validator and user controller - done
    //add validations for length of firstName, lastName, username fields - done
    //add unique username validation (userRepository.existsByUsername)  - done
    //add a phone subresource (liquibase, entity, assembler, controller, tests etc) (look up jpa OneToMany)

 //Additional tasks
//
//    create a table and entity to track the history of name changes. When an update is called on the user resource, and either the first name or last name is changed, write a row to the user_name_change_history table. Also create a rest endpoint (and any additional classes) to GET the name change hsitory for a user.
//
//            2.
//    create a phone number verification flow. create 2 additional rest endpoints as "actions" on phone
//
///api/v1/users/{userId}/phones/{phoneId}/initiateVerification
//this will send a 6 digit code to the phone number via sms using twilio.
//
//
//            /api/v1/users/{userId}/phones/{phoneId}/completeVerification
//this will accept the 6 digit code in the request body. if it matches the value sent out in the previous request, the phone number will be marked as verified.
//
//
//for twilio, google "twilio java api example". You'll need to sign up with a test account and add a dependency to the build.gradle. remember to do a gradle refresh in your IDE to pull down the jar. Only send sms messages to your phone number and don't check in your twilio api key (or they will suspend your account)



    public static void main(String[] args) {
        SpringApplication.run(OnboardingMicroserviceApplication.class, args);
    }
}
