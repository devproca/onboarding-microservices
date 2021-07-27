package ca.devpro.service;

import ca.devpro.assembler.PhoneAssembler;
import ca.devpro.dto.CompleteVerificationDto;
import ca.devpro.dto.PhoneDto;
import ca.devpro.dto.SmsRequestDto;
import ca.devpro.entity.Phone;
import ca.devpro.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
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

    @Autowired
    private SmsService smsService;

    public PhoneDto create(PhoneDto dto) {
        phoneValidator.validateAndThrow(dto);
        Phone phone = phoneAssembler.disassemble(dto);
        Phone newPhone = phoneRepository.save(phone);
        return phoneAssembler.assemble(newPhone);
    }

    public List<PhoneDto> findByUserId(UUID userId) {
        return phoneRepository.findByUserId(userId)
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

    public void initiateVerification(UUID phoneId, UUID userId) {
                Phone phone = phoneRepository.findByUserIdAndPhoneId(userId, phoneId);
                String codeVerify = SmsService.codeVerifyGenerator();
                phone.setCodeVerify(codeVerify);
                phoneRepository.save(phone);
                SmsRequestDto smsRequestDto = new SmsRequestDto();
                smsRequestDto.setPhoneNumber(phone.getPhoneNumber());
                smsRequestDto.setMessage(codeVerify);
                smsService.sendSms(smsRequestDto);
    }

    public void completeVerification(CompleteVerificationDto completeVerificationDto, UUID userId, UUID phoneId) {
        Phone phone = phoneRepository.findByUserIdAndPhoneId(userId, phoneId);
        if (completeVerificationDto.getCode().equals(phone.getCodeVerify())) {
            phone.setIsVerified(true);
            phoneRepository.save(phone);
        }
    }
}
