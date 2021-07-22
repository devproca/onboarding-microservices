package ca.devpro.controller;

import ca.devpro.dto.PhoneDto;
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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)

    public PhoneDto create(@PathVariable("userId") UUID userId, @RequestBody() PhoneDto dto) {
        String uid = userId.toString();
        dto.setUserId(uid);
        return phoneService.create(dto);
    }
    @GetMapping()
    public List<PhoneDto> findByUserId(@PathVariable("userId") String userId) {
        String uid = userId.toString();
        return phoneService.findByUserId(uid);
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
}

