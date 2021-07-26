package ca.devpro.service;

import ca.devpro.assembler.UserAssembler;
import ca.devpro.dto.ChangenameDto;
import ca.devpro.dto.UserDto;
import ca.devpro.entity.User;
import ca.devpro.exception.NotFoundExpection;
import ca.devpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAssembler userAssembler;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private ChangenameService changenameService;

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userAssembler::assemble)
                .sorted(Comparator.comparing(UserDto::getLastName)
                        .thenComparing(UserDto::getFirstName))
                .collect(Collectors.toList());
    }

    public UserDto create(UserDto dto) {
        userValidator.validateAndThrow(dto);
        User entity = userAssembler.disassemble(dto);
        userRepository.save(entity);
        return userAssembler.assemble(entity);
    }

    public UserDto get(UUID userId) {
        return userRepository.findById(userId)
                .map(entity -> userAssembler.assemble(entity))
                .orElseThrow(() -> new NotFoundExpection());
    }

    public UserDto update(UserDto dto) {
        userValidator.validateAndThrow(dto);
        return userRepository.findById(dto.getUserId())
                .stream()
                .peek(entity -> {
                    // entity - old, dto - new
                    System.out.println(entity.getFirstName() + " : " + entity.getLastName());
                    System.out.println(dto);
                    Date date = new Date();
                    ChangenameDto changeName = new ChangenameDto()
                            .setUserId(dto.getUserId())
                            .setFirstName(dto.getFirstName())
                            .setLastName(dto.getLastName())
                            .setCreatedDate(new Timestamp(date.getTime()));
                    changenameService.createChangename(changeName, entity.getFirstName(), entity.getLastName());
                })
                .map(entity -> userAssembler.disassembleInto(dto, entity))
                .map(userRepository::save)
                .map(userAssembler::assemble)
                // not sure if I need this
                .findFirst()
                .orElseThrow(NotFoundExpection::new);
    }

    public void delete(UUID userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new NotFoundExpection();
        });
    }

}
