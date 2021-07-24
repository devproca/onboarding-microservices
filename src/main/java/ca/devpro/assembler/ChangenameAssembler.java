package ca.devpro.assembler;

import ca.devpro.dto.ChangenameDto;
import ca.devpro.entity.Changename;
import org.springframework.stereotype.Component;

@Component
public class ChangenameAssembler {

    public ChangenameDto assemble(Changename entity) {
        return new ChangenameDto()
                .setChangenameId(entity.getChangenameId())
                .setUserId(entity.getUserId())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName())
                .setCreatedDate(entity.getCreatedDate());
    }

    public Changename disassemble(ChangenameDto dto) {
        Changename entity = Changename.newInstance(dto.getUserId());
        return disassembleInto(dto, entity);
    }

    public Changename disassembleInto(ChangenameDto dto, Changename entity) {
        return entity
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setCreatedDate(dto.getCreatedDate());
    }

}
