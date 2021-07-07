package ca.devpro.service;

import ca.devpro.api.UserDto;
import ca.devpro.assembler.UserAssembler;
import ca.devpro.entity.User;
import ca.devpro.exception.NotFoundException;
import ca.devpro.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserAssembler userAssembler;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public UserDto create(UserDto dto) {
        userValidator.validateAndThrow(dto);
        User entity = userAssembler.disassemble(dto);
        userRepository.save(entity);
        return userAssembler.assemble(entity);
    }

    public UserDto get(UUID userId) {
        return userRepository.findById(userId)
                .map(userAssembler::assemble)
                .orElseThrow(NotFoundException::new);
    }

    public UserDto update(UserDto dto) {
        userValidator.validateAndThrow(dto);
        return userRepository.findById(dto.getUserId())
                .map(entity -> userAssembler.disassembleInto(dto, entity))
                .map(userRepository::save)
                .map(userAssembler::assemble)
                .orElseThrow(NotFoundException::new);
    }

    public void delete(UUID userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new NotFoundException();
        });
    }
}
