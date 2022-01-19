package ca.devpro.core.user.service;

import ca.devpro.api.UserDto;
import ca.devpro.core.user.assembler.UserAssembler;
import ca.devpro.core.user.entity.User;
import ca.devpro.core.user.repository.UserRepository;
import ca.devpro.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    public List<UserDto> find() {
        return userRepository.findAll()
                .stream()
                .map(userAssembler::assemble)
                .collect(Collectors.toList());
    }

    public UserDto get(UUID userId) {
        return userRepository.findById(userId)
                .map(userAssembler::assemble)
                .orElseThrow(NotFoundException::new);
    }

    public UserDto save(UserDto dto) {
        userValidator.validateAndThrow(dto);
        User entity = userAssembler.disassemble(dto);
        userRepository.save(entity);
        return userAssembler.assemble(entity);
    }
}
