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
    private PhoneRepository phoneRepository;
    @Autowired
    private PhoneValidator phoneValidator;

    public PhoneDto create(PhoneDto dto) {
        phoneValidator.validateAndThrow(dto);
        Phone entity = phoneAssembler.disassemble(dto);
        phoneRepository.save(entity);
        return phoneAssembler.assemble(entity);
    }

    public List<PhoneDto> findAll() {
        return phoneRepository.findAll()
                .stream()
                .map(phoneAssembler::assemble)
                .collect(Collectors.toList());
    }

    public PhoneDto get(UUID phoneId) {
        return phoneRepository.findById(phoneId)
                .map(phoneAssembler::assemble)
                .orElseThrow(() -> new NotFoundException());
    }

    public PhoneDto update(PhoneDto dto) {
        phoneValidator.validateAndThrow(dto);
        return phoneRepository.findById(dto.getPhoneId())
                .map(entity -> phoneAssembler.disassembleInto(dto, entity))
                .map(phoneRepository::save)
                .map(phoneAssembler::assemble)
                .orElseThrow(() -> new NotFoundException());
    }

    public void delete(UUID phoneId) {
        phoneRepository.findById(phoneId).ifPresentOrElse(phoneRepository::delete, () -> {
            throw new NotFoundException();
        });
    }

    public void initiateVerification(UUID phoneId) {
        Phone phone = phoneRepository.findById(phoneId).get();
        String verificationKey = VerificationService.generateVerifyCode();

        phone.setVerificationKey(verificationKey);
        phoneRepository.save(phone);

        verificationService.InitiateVerification(phone.getPhoneNumber(), verificationKey);
    }

    public void completeVerification(String verificationKey,  UUID phoneId) {
        Phone phone = phoneRepository.findById(phoneId).get();

        if (phone.getVerificationKey().equals(verificationKey)){
            phone.setIsVerified(true);
        }

        phoneRepository.save(phone);
    }



}
