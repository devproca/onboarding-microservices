package ca.devpro.assembler;

import ca.devpro.api.UserDto;
import ca.devpro.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserAssembler {

    @Autowired
    private PhoneAssembler phoneAssembler;

    public UserDto assemble(User entity) {
        return new UserDto()
                .setUserId(entity.getUserId())
                .setLastName(entity.getLastName())
                .setFirstName(entity.getFirstName())
                .setUsername(entity.getUsername())
                .setPhones(entity.getPhones().stream().map(phoneAssembler::assemble).collect(Collectors.toList()));
    }

    //create
    public User disassemble(UserDto dto) {
        User entity = User.newInstance(dto.getUsername());
        return disassembleInto(dto, entity);
    }

    //update
    public User disassembleInto(UserDto dto, User entity) {
        return entity.setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setPhones(phoneAssembler.disassembleIntoParentEntity(dto.getPhones(), entity));
    }
}
