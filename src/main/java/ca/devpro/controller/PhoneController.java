package ca.devpro.controller;

import ca.devpro.api.PhoneDto;
import ca.devpro.api.PhoneVerificationDto;
import ca.devpro.service.PhoneService;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneDto createPhone(@RequestBody PhoneDto dto) {
        return phoneService.createPhone(dto);
    }

    @GetMapping()
    public List<PhoneDto> findAllPhones(@PathVariable("userId") UUID userId) {
        return phoneService.findAllPhones(userId);
    }

    @GetMapping("/{phoneId}")
    public PhoneDto getPhone(@PathVariable("phoneId") UUID phoneId) {
        return phoneService.getPhone(phoneId);
    }

    @PutMapping("/{phoneId}")
    public PhoneDto updatePhone(@PathVariable("phoneId") UUID phoneId, @RequestBody PhoneDto dto) {
        dto.setPhoneId(phoneId);
        return phoneService.updatePhone(dto);
    }

    @DeleteMapping("/{phoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhone(@PathVariable("phoneId") UUID phoneId) {
        phoneService.deletePhone(phoneId);
    }

    @PostMapping("/{phoneId}/initiateVerification")
    public void initiateVerification(@PathVariable("phoneId") UUID phoneId) {
        phoneService.initiateVerification(phoneId);
    }

    @PostMapping("/{phoneId}/completeVerification")
    public void completeVerification(@PathVariable("phoneId") UUID phoneId, @RequestBody PhoneVerificationDto dto) {
        phoneService.completeVerification(dto.getCode(), phoneId);
    }



    //264517
}
