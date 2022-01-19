package ca.devpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
public class OnboardingMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnboardingMicroserviceApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("Fake person! fixme");
    }
}
