package ca.devpro.assembler;

import ca.devpro.api.PhoneNumberDto;
import ca.devpro.entity.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberAssembler {

    public PhoneNumberDto assemble(PhoneNumber entity) {
        return new PhoneNumberDto()
                .setPhoneId(entity.getPhoneId())
                .setUserId(entity.getUserId())
                .setPhoneNumber(entity.getPhoneNumber())
                .setIsVerified(entity.isVerified());
    }

    public PhoneNumber disassemble(PhoneNumberDto dto) {
        PhoneNumber entity = PhoneNumber.newInstance(dto.getUserId());

        return disassembleInto(dto, entity);
    }

    public PhoneNumber disassembleInto(PhoneNumberDto dto, PhoneNumber entity) {
        return entity.setPhoneNumber(dto.getPhoneNumber());
    }
}
