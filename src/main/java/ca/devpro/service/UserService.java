package ca.devpro.service;

import ca.devpro.api.UserDto;
import ca.devpro.assembler.UserAssembler;
import ca.devpro.entity.User;
import ca.devpro.exception.NotFoundException;
import ca.devpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserAssembler userAssembler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;

    public List<UserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(userAssembler::assemble)
                .collect(Collectors.toList());
    }

    public UserDto create(UserDto dto) {
        userValidator.validateAndThrow(dto);
        User entity = userAssembler.disassemble(dto);
        User updatedEntity = userRepository.save(entity);
        return userAssembler.assemble(updatedEntity);
    }

    public UserDto update(UserDto dto) {
        userValidator.validateAndThrow(dto);
        return userRepository.findById(dto.getUserId())
                .map(entity -> userAssembler.disassembleInto(dto, entity))
                .map(userRepository::save)
                .map(userAssembler::assemble)
                .orElseThrow(NotFoundException::new);
    }

    public UserDto get(UUID userId) {
        return userRepository.findById(userId)
                .map(userAssembler::assemble)
                .orElseThrow(NotFoundException::new);
    }

    public void delete(UUID userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new NotFoundException();
                });
    }
}
