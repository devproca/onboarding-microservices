package ca.devpro.repository;

import ca.devpro.dto.PhoneDto;
import ca.devpro.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhoneRepository extends JpaRepository<Phone, UUID> {
    List<Phone> findByUserId(String userId);
    boolean existsByPhoneNumberIgnoreCase(String phonenumber);
}


