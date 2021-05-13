package ca.devpro.service;

import ca.devpro.api.PhoneDto;
import ca.devpro.assembler.PhoneAssembler;
import ca.devpro.config.TwilioConfiguration;
import ca.devpro.entity.Phone;
import ca.devpro.exception.NotFoundException;
import ca.devpro.exception.ValidationException;
import ca.devpro.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
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
    @Autowired

    private static TwilioConfiguration twilioConfiguration;

    private SmsService smsService;


    public PhoneDto createPhone(PhoneDto dto) {
        phoneValidator.validateAndThrow(dto);
        Phone entity = phoneAssembler.disassemble(dto);
        phoneRepository.save(entity);
        return phoneAssembler.assemble(entity);
    }

    public List<PhoneDto> findAllPhones() {
        return phoneRepository.findAll()
                .stream()
                .map(phoneAssembler::assemble)
                .collect(Collectors.toList());
    }

    public PhoneDto getPhone(UUID phoneId) {
        return phoneRepository.findById(phoneId)
                .map(phoneAssembler::assemble)
                .orElseThrow(() -> new NotFoundException());
    }

    public PhoneDto updatePhone(PhoneDto dto) {
        phoneValidator.validateAndThrow(dto);
        return phoneRepository.findById(dto.getPhoneId())
                .map(entity -> phoneAssembler.disassembleInto(dto, entity))
                .map(phoneRepository::save)
                .map(phoneAssembler::assemble)
                .orElseThrow(() -> new NotFoundException());
    }

    public void deletePhone(UUID phoneId) {
        phoneRepository.findById(phoneId).ifPresentOrElse(phoneRepository::delete, () -> {
            throw new NotFoundException();
        });
    }

    public void initiateVerification(UUID phoneId) {
        Phone phone = phoneRepository.findById(phoneId).orElseThrow(NotFoundException::new);
        String verificationKey = createVerificationKey();

        phone.setVerificationKey(verificationKey);
        phoneRepository.save(phone);
        smsService.sendSmsMessage(phone.getPhoneNumber(), verificationKey);
    }


    public void completeVerification(String verificationKey, UUID phoneId) {
        Phone phone = phoneRepository.findById(phoneId).orElseThrow(NotFoundException::new);
        if (phone.getVerificationKey().equals(verificationKey)){
            phone.setIsVerified(true);
            phoneRepository.save(phone);
        } else {
            throw new ValidationException(Collections.singletonMap("message", "you got the wrong code!"));
        }

    }

    private String createVerificationKey() {
        Random rnd = new Random();
        int key = rnd.nextInt(999999);
        return String.format("%06d", key);
    }
}


