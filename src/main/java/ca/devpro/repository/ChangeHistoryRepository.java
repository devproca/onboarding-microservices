package ca.devpro.repository;

import ca.devpro.entity.ChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChangeHistoryRepository extends JpaRepository<ChangeHistory, UUID> {
    boolean existsByUpdatedUserNameIgnoreCase(String updatedUsername);

}
