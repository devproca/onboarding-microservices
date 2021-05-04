package ca.devpro.assembler;

import ca.devpro.api.PhoneDto;
import ca.devpro.entity.Phone;
import org.springframework.stereotype.Component;

@Component
public class PhoneAssembler {

    public PhoneDto assemble(Phone entity) {
        return new PhoneDto()
                .setPhoneId(entity.getPhoneId())
                .setPhoneNumber(entity.getPhoneNumber());

    }

    public Phone disassemble(PhoneDto dto) {
        Phone entity = Phone.newInstance(dto.getPhoneNumber());
        return disassembleInto(dto, entity);
    }

    public Phone disassembleInto(PhoneDto dto, Phone entity) {
        return entity.setPhoneNumber(dto.getPhoneNumber());

    }


}
