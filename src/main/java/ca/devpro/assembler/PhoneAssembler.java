package ca.devpro.assembler;

import ca.devpro.api.PhoneDto;
import ca.devpro.entity.Phone;
import org.springframework.stereotype.Component;

@Component
public class PhoneAssembler {

    public PhoneDto assemble(Phone entity){
        return new PhoneDto()
                .setPhoneId(entity.getPhoneId())
                .setUserId(entity.getUserId())
                .setPhoneNumber(entity.getPhoneNumber())
                .setPhoneType(entity.getPhoneType());
    }

    // create
    public Phone disassemble(PhoneDto dto){
        Phone entity = Phone.newPhone(dto.getUserId());
        return disassembleInto(dto, entity);
    }

    // update
    public Phone disassembleInto(PhoneDto dto, Phone entity){
        return entity.setPhoneNumber(dto.getPhoneNumber())
                .setPhoneType(dto.getPhoneType());
    }
}
