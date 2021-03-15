package ca.devpro.controller;

import ca.devpro.api.PhoneNumberDto;
import ca.devpro.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/phonenumbers")
public class PhoneNumberController {

    @Autowired
    private PhoneNumberService phoneNumberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneNumberDto create(@RequestBody PhoneNumberDto dto) {
        return phoneNumberService.create(dto);
    }

    @PutMapping("/{phoneId}")
    public PhoneNumberDto update(@PathVariable("userId") UUID userId, @PathVariable("phoneId") UUID phoneId, @RequestBody PhoneNumberDto dto) {
        dto.setUserId(userId);
        dto.setPhoneId(phoneId);
        return phoneNumberService.update(dto);
    }

    @GetMapping()
    public List<PhoneNumberDto> findAll(@PathVariable("userId") UUID userId) {
        return phoneNumberService.findAll(userId);
    }

    @GetMapping("/{phoneId}")
    public PhoneNumberDto get(@PathVariable("userId") UUID userId, @PathVariable("phoneId") UUID phoneId) {
        return phoneNumberService.get(userId, phoneId);
    }

    @DeleteMapping("/{phoneId}")
    public void delete(@PathVariable("userId") UUID userId, @PathVariable("phoneId") UUID phoneId) {
        phoneNumberService.delete(userId, phoneId);
    }
}
