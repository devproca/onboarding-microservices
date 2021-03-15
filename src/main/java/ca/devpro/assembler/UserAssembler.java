package ca.devpro.assembler;

import ca.devpro.api.UserDto;
import ca.devpro.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    public UserDto assemble(User entity) {
        return new UserDto()
                .setUserId(entity.getUserId())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName())
                .setUsername(entity.getUsername());
    }

    public User disassemble(UserDto dto) {
        User entity = User.newInstance(dto.getUsername());
        return disassembleInto(dto, entity);
    }

    public User disassembleInto(UserDto dto, User entity) {
        return entity
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName());
    }
}
