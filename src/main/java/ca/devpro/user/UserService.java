package ca.devpro.user;

import ca.devpro.api.UserDto;
import ca.devpro.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserAssembler userAssembler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;

    public UserDto create(UserDto dto) {
        //validate dto
        //turn the dto into an entity
        //save the entity
        //turn the entity back into a dto and return it
        userValidator.validateAndThrow(dto);
        User entity = userAssembler.disassemble(dto);
        userRepository.save(entity);
        return userAssembler.assemble(entity);
    }

    public UserDto get(UUID userId) {
        return userRepository.findById(userId)
                .map(entity -> userAssembler.assemble(entity))
                .orElseThrow(() -> new NotFoundException());
    }

    public UserDto update(UserDto dto) {
        //validate dto
        //find the existing record
        //jam the dto fields into the existing record
        //save it
        //turn the entity back into a dto and return it
        userValidator.validateAndThrow(dto);
        return userRepository.findById(dto.getUserId())
                .map(entity -> userAssembler.disassembleInto(dto, entity))
                .map(userRepository::save)
                .map(userAssembler::assemble)
                .orElseThrow(NotFoundException::new);
    }

    public void delete(UUID userId) {
        userRepository.findById(userId).ifPresentOrElse(entity -> {
            userRepository.delete(entity);
        }, () -> {
            throw new NotFoundException();
        });
    }

}
