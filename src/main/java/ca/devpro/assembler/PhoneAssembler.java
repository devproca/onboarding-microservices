package ca.devpro.assembler;

import ca.devpro.dto.PhoneDto;
import ca.devpro.entity.Phone;
import org.springframework.stereotype.Component;

@Component
public class PhoneAssembler {
    public PhoneDto assemble(Phone phone) {
        return new PhoneDto()
                .setPhoneId(phone.getPhoneId())
                .setUserId(phone.getUserId())
                .setPhoneNumber(phone.getPhoneNumber());
    }

    public Phone disassemble(PhoneDto dto) {
        Phone phone = Phone.newInstance(dto.getUserId());

        return disassembleInto(dto, phone);
    }
    public Phone disassembleInto(PhoneDto dto, Phone phone) {
        return phone
            .setPhoneNumber(dto.getPhoneNumber());
    }

}

