package ca.devpro.service;

import ca.devpro.api.ChangeHistoryDto;
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
    @Autowired
    private ChangeHistoryService changeHistoryService;

    public UserDto create(UserDto dto) {
        userValidator.validateAndThrow(dto);
        User entity = userAssembler.disassemble(dto);
        userRepository.save(entity);
        return userAssembler.assemble(entity);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userAssembler::assemble)
                .collect(Collectors.toList());
    }

    public UserDto get(UUID userId) {
        return userRepository.findById(userId)
                .map(userAssembler::assemble)
                .orElseThrow(() -> new NotFoundException());
    }

    public UserDto update(UserDto dto) {
        userValidator.validateAndThrow(dto);
        return userRepository.findById(dto.getUserId())
                .stream()
                .peek(entity -> {
                    ChangeHistoryDto nameChange = new ChangeHistoryDto()
                            .setUserId(entity.getUserId())
                            .setPreviousFirstName(entity.getFirstName())
                            .setUpdatedFirstName(dto.getFirstName())
                            .setPreviousLastName(entity.getLastName())
                            .setUpdatedLastName(dto.getLastName())
                            .setPreviousUsername(entity.getUsername())
                            .setUpdatedUsername(dto.getUsername());

                    changeHistoryService.update(nameChange);
                })
                .map(entity -> userAssembler.disassembleInto(dto, entity))
                .map(userRepository::save)
                .map(userAssembler::assemble)
                .findFirst()
                .orElseThrow(() -> new NotFoundException());
    }

    public void delete(UUID userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new NotFoundException();
        });
    }
}


