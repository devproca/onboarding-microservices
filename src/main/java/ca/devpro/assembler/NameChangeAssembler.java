package ca.devpro.assembler;

import ca.devpro.api.NameChangeDto;
import ca.devpro.api.UserDto;
import ca.devpro.entity.NameChange;
import ca.devpro.entity.User;
import org.springframework.stereotype.Component;

@Component
public class NameChangeAssembler {
    public NameChangeDto assemble(NameChange entity) {
        return new NameChangeDto()
                .setNameChangeId(entity.getNameChangeId())
                .setUserId(entity.getUserId())
                .setPreviousFirstName(entity.getPreviousFirstName())
                .setPreviousLastName(entity.getPreviousLastName())
                .setPreviousUsername(entity.getPreviousUsername())
                .setUpdatedFirstName(entity.getUpdatedFirstName())
                .setUpdatedLastName(entity.getUpdatedLastName())
                .setUpdatedUsername(entity.getUpdatedUsername());
    }

    public NameChange disassemble(NameChangeDto dto) {
        NameChange entity = NameChange.newInstance(dto);
        return disassembleInto(dto, entity);
    }

    public NameChange disassembleInto(NameChangeDto dto, NameChange entity) {
        return entity.setPreviousFirstName(dto.getPreviousFirstName())
                .setPreviousLastName(dto.getPreviousLastName())
                .setPreviousUsername(dto.getPreviousUsername())
                .setUpdatedFirstName(dto.getUpdatedFirstName())
                .setUpdatedLastName(dto.getUpdatedLastName())
                .setUpdatedUsername(dto.getUpdatedUsername());
    }
}
