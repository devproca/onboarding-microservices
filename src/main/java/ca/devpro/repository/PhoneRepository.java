package ca.devpro.repository;

import ca.devpro.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhoneRepository extends JpaRepository<Phone, UUID> {
    List<Phone> findByUserId(UUID userId);
    Phone findByUserIdAndPhoneId(UUID userId, UUID phoneId);
    boolean existsByPhoneNumberIgnoreCase(String phoneNumber);
}


