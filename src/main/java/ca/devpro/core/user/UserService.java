package ca.devpro.core.user;

import ca.devpro.core.user.entity.User;
import ca.devpro.core.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get(UUID userID) {
        return userRepository.findById(userID)
                .orElseThrow(NotFoundException::new);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
