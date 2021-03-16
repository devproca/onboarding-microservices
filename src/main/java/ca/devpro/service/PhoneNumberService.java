package ca.devpro.service;

import ca.devpro.api.PhoneNumberDto;
import ca.devpro.api.VerificationDto;
import ca.devpro.assembler.PhoneNumberAssembler;
import ca.devpro.entity.PhoneNumber;
import ca.devpro.exception.NotFoundException;
import ca.devpro.repository.PhoneNumberRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhoneNumberService {

    @Autowired
    private PhoneNumberAssembler phoneNumberAssembler;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private PhoneNumberValidator phoneNumberValidator;

    @Autowired
    private VerificationService verificationService;

    public List<PhoneNumberDto> findAll(UUID userId) {
        return phoneNumberRepository
                .findAllByUserId(userId)
                .stream()
                .map(entity -> phoneNumberAssembler.assemble(entity))
                .collect(Collectors.toList());
    }

    public PhoneNumberDto create(PhoneNumberDto dto) {
        phoneNumberValidator.validateAndThrow(dto);
        PhoneNumber entity = phoneNumberAssembler.disassemble(dto);
        phoneNumberRepository.save(entity);

        return phoneNumberAssembler.assemble(entity);
    }

    public PhoneNumberDto get(UUID userId, UUID phoneId) {
        return phoneNumberRepository.findByUserIdAndPhoneId(userId, phoneId)
                .map(phoneNumberAssembler::assemble)
                .orElseThrow(() -> new NotFoundException());
    }

    public PhoneNumberDto update(PhoneNumberDto dto) {
        phoneNumberValidator.validateAndThrow(dto);

        return phoneNumberRepository.findByUserIdAndPhoneId(dto.getUserId(), dto.getPhoneId())
                .map(entity -> phoneNumberAssembler.disassembleInto(dto, entity))
                .map(phoneNumberRepository::save)
                .map(phoneNumberAssembler::assemble)
                .orElseThrow(() -> new NotFoundException());
    }

    public void delete(UUID userId, UUID phoneId) {
        phoneNumberRepository.findByUserIdAndPhoneId(userId, phoneId).ifPresentOrElse(phoneNumberRepository::delete, () -> {
           throw new NotFoundException();
        });
    }

    public void deleteAll(UUID userId) {
        if (phoneNumberRepository.existsByUserId(userId)) {
            phoneNumberRepository.findAllByUserId(userId)
                    .stream()
                    .forEach(phoneNumberRepository::delete);
        } else {
            throw new NotFoundException();
        }
    }

    public void sendVerifyCode(UUID userId, UUID phoneId) {
        PhoneNumber phone = phoneNumberRepository.findByUserIdAndPhoneId(userId, phoneId).get();
        String verifyCode = VerificationService.generateVerifyCode();

        phone.setVerifyCode(verifyCode);
        phoneNumberRepository.save(phone);

        verificationService.sendVerifyCode(phone.getPhoneNumber(), verifyCode);
    }

    public void verifyCode(VerificationDto dto, UUID userId, UUID phoneId) {
        PhoneNumber phone = phoneNumberRepository.findByUserIdAndPhoneId(userId, phoneId).get();

        if (phone.getVerifyCode().equals(dto.getVerifyCode())) {
            phone.setNumberVerified(true);
        }

        phoneNumberRepository.save(phone);
    }
}
