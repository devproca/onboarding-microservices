package ca.devpro.controller;

import ca.devpro.api.PhoneDto;
import ca.devpro.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/phones")
public class PhoneController {


    @Autowired
    private PhoneService phoneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneDto create(@RequestBody PhoneDto dto, @PathVariable("userId") UUID userId) {
        dto.setUserId(userId);
        return phoneService.create(dto);
    }

    @GetMapping()
    public List<PhoneDto> findAll(@PathVariable("userId") UUID userId) {
        return phoneService.findAll(userId);
    }

    @GetMapping("/{userId}")
    public PhoneDto get(@PathVariable("phoneId") UUID phoneId, @PathVariable("userId") UUID userId) {
        return phoneService.get(phoneId, userId);
    }

    @PutMapping("/{userId}")
    public PhoneDto update(@PathVariable("phoneId") UUID phoneId, @RequestBody PhoneDto dto, @PathVariable("userId") UUID userId) {
        dto.setUserId(userId);
        dto.setPhoneId(phoneId);
        return phoneService.update(dto);
    }

    @DeleteMapping("/{phoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("phoneId") UUID phoneId, @PathVariable("userId") UUID userId) {
        phoneService.delete(phoneId, userId);
    }



}
