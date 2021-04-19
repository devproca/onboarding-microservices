package ca.devpro.assembler;

import ca.devpro.api.UserDto;
import ca.devpro.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    public UserDto assemble(User entity) {
        return new UserDto()
                .setUserId(entity.getUserId())
                .setLastName(entity.getLastName())
                .setFirstName(entity.getFirstName())
                .setUsername(entity.getUsername());
    }

    //create
    public User disassemble(UserDto dto) {
        User entity = User.newInstance(dto.getUsername());
        return disassembleInto(dto, entity);
    }

    //update
    public User disassembleInto(UserDto dto, User entity) {
        return entity.setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName());
    }
}
