package ca.devpro.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    public List<User> findAllByFirstNameBetweenOrLastNameBetween(String firstNameBefore, String firstNameAfter, String lastNameBefore, String lastNameAfter);

    public List<User> findAllByFirstNameIsNotContainingIgnoreCase(String firstName);



}
