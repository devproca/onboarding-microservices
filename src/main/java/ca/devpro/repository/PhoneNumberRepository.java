package ca.devpro.repository;

import ca.devpro.entity.PhoneNumber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, UUID> {

    List<PhoneNumber> findAllByUserId(UUID userId);

    Optional<PhoneNumber> findByUserIdAndPhoneId(UUID userId, UUID phoneId);

    boolean existsByUserId(UUID userId);
}
