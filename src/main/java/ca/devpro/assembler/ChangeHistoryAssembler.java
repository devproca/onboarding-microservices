
package ca.devpro.assembler;

import ca.devpro.api.ChangeHistoryDto;
import ca.devpro.entity.ChangeHistory;
import org.springframework.stereotype.Component;

@Component
public class ChangeHistoryAssembler {

    public ChangeHistoryDto assemble(ChangeHistory entity) {
        return new ChangeHistoryDto()
                .setVersionId(entity.getNewId())
                .setUserId(entity.getUserId())
                .setPreviousFirstName(entity.getPreviousFirstName())
                .setPreviousLastName(entity.getPreviousLastName())
                .setPreviousUsername(entity.getPreviousUsername())
                .setUpdatedFirstName(entity.getUpdatedFirstName())
                .setUpdatedLastName(entity.getUpdatedLastName())
                .setUpdatedUsername(entity.getUpdatedUsername());
    }

    public ChangeHistory disassemble(ChangeHistoryDto dto) {
        ChangeHistory entity = ChangeHistory.newInstance(dto);
        return disassembleInto(dto, entity);
    }

    public ChangeHistory disassembleInto(ChangeHistoryDto dto, ChangeHistory entity) {
        return entity.setPreviousFirstName(dto.getPreviousFirstName())
                .setUpdatedFirstName(dto.getUpdatedFirstName())
                .setPreviousLastName(dto.getPreviousLastName())
                .setUpdatedLastName(dto.getUpdatedLastName())
                .setPreviousUsername(dto.getPreviousUsername())
                .setUpdatedUsername(dto.getUpdatedUsername());
    }
}