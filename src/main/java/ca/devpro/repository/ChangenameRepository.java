package ca.devpro.repository;

import ca.devpro.entity.Changename;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ChangenameRepository  extends JpaRepository<Changename, UUID> {

}
