package ca.devpro.repository;

import ca.devpro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsernameIgnoreCase(String username);
}



//controller : responsible for http things
//service: responsible for business logic validation, orchestration etc
//repository: resposible for interacting with the database
