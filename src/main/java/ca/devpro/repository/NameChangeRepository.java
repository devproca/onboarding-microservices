package ca.devpro.repository;

import ca.devpro.entity.NameChange;
import ca.devpro.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NameChangeRepository extends JpaRepository<NameChange, UUID> {

}