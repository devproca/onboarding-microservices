package ca.devpro.core.user.assembler;

import ca.devpro.api.UserDto;
import ca.devpro.core.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    public UserDto assemble(User entity) {
        return new UserDto()
                .setUserId(entity.getUserId())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName())
                .setEmailAddress(entity.getEmailAddress())
                .setPassword("****");
    }

    //creating
    public User disassemble(UserDto dto) {
        User entity = User.newInstance(dto.getEmailAddress(), dto.getPassword());
        return disassembleInto(dto, entity);
    }

    //updating
    public User disassembleInto(UserDto dto, User entity) {
        return entity.setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName());
    }
}
