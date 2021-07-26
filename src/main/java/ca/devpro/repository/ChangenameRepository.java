package ca.devpro.repository;

import ca.devpro.entity.Changename;
import ca.devpro.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChangenameRepository  extends JpaRepository<Changename, UUID> {
    List<Changename> findByUserId(UUID userId);
}
