package ca.devpro.controller;

import ca.devpro.dto.CompleteVerificationDto;
import ca.devpro.dto.PhoneDto;
import ca.devpro.service.PhoneService;
import ca.devpro.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/phones")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;
    @Autowired
    private SmsService smsService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneDto create(@PathVariable("userId") UUID userId, @RequestBody() PhoneDto dto) {
        dto.setUserId(userId);
        return phoneService.create(dto);
    }

    @GetMapping()
    public List<PhoneDto> findByUserId(@PathVariable("userId") UUID userId) {
        return phoneService.findByUserId(userId);
    }

    @PutMapping("/{phoneId}")
    public PhoneDto update(@PathVariable("phoneId") UUID phoneId, @RequestBody() PhoneDto dto) {
        dto.setPhoneId(phoneId);
        return phoneService.update(dto);
    }

    @DeleteMapping("/{phoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("phoneId") UUID phoneId) {
        phoneService.delete(phoneId);
    }

    @PostMapping("/{phoneId}/initiateVerification")
    public void initiateVerification(@PathVariable("userId") UUID userId, @PathVariable("phoneId") UUID phoneId) {
       phoneService.initiateVerification(phoneId, userId);

    }

    @PostMapping("/{phoneId}/completeVerification")
    public void completeVerification(@RequestBody CompleteVerificationDto completeVerificationDto, @PathVariable("userId") UUID userId, @PathVariable("phoneId") UUID phoneId) {
        phoneService.completeVerification(completeVerificationDto, userId, phoneId);
    }
}