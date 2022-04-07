package ca.devpro.user;

import ca.devpro.api.UserDto;
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
