package ca.devpro.service;

import ca.devpro.api.PhoneDto;
import ca.devpro.assembler.PhoneAssembler;
import ca.devpro.entity.Phone;
import ca.devpro.exception.NotFoundException;
import ca.devpro.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhoneService {

    @Autowired
    private PhoneAssembler phoneAssembler;

    @Autowired
    private PhoneValidator phoneValidator;

    @Autowired
    private PhoneRepository phoneRepository;

    public PhoneDto create(PhoneDto dto) {
        phoneValidator.validateAndThrow(dto);
        Phone entity = phoneAssembler.disassemble(dto);
        Phone updatedEntity = phoneRepository.save(entity);
        return phoneAssembler.assemble(updatedEntity);
    }

    public PhoneDto get(UUID phoneId) {
        return phoneRepository.findById(phoneId)
                .map(phoneAssembler::assemble)
                .orElseThrow(NotFoundException::new);
    }

    public List<PhoneDto> findAll() {
        return phoneRepository
                .findAll()
                .stream()
                .map(phoneAssembler::assemble)
                .collect(Collectors.toList());
    }

    public PhoneDto update(PhoneDto dto) {
        phoneValidator.validateAndThrow(dto);
        return phoneRepository.findById(dto.getPhoneId())
                .map(entity -> phoneAssembler.disassembleInto(dto, entity))
                .map(phoneRepository::save)
                .map(phoneAssembler::assemble)
                .orElseThrow(NotFoundException::new);
    }

    public void delete(UUID phoneId) {
        phoneRepository.findById(phoneId)
                .ifPresentOrElse(phoneRepository::delete, () -> {
                    throw new NotFoundException();
                });
    }
}
