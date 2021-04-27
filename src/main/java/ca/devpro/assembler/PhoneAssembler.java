package ca.devpro.assembler;

import ca.devpro.api.PhoneDto;
import ca.devpro.entity.Phone;
import ca.devpro.entity.User;
import ca.devpro.util.CollectionComparator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneAssembler {

    public PhoneDto assemble(Phone entity){
        return new PhoneDto()
                .setPhoneId(entity.getPhoneId())
                .setUserId(entity.getUserId())
                .setPhoneNumber(entity.getPhoneNumber())
                .setPhoneType(entity.getPhoneType())
                .setVarificationStatus(entity.isVarificationStatus());
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

    public List<Phone> disassembleIntoParentEntity(List<PhoneDto> dtos, User parentEntity) {
        CollectionComparator.of(parentEntity.getPhones(), dtos)
                .compareWith((entity, dto) -> entity.getPhoneId().equals(dto.getPhoneId()))
                .ifAdded(dto -> {
                    dto.setUserId(parentEntity.getUserId());
                    parentEntity.getPhones().add(disassemble(dto));
                })
                .ifRemoved(entity -> {
                    parentEntity.getPhones().remove(entity);
                })
                .ifExists((entity, dto) -> {
                    disassembleInto(dto, entity);
                });
        return parentEntity.getPhones();
    }
}
