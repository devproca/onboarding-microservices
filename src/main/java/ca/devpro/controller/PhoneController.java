package ca.devpro.controller;

import ca.devpro.api.PhoneDto;
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
    public PhoneDto create(@RequestBody() PhoneDto dto) {
        return phoneService.create(dto);
    }

    @GetMapping("/{phoneId}")
    public PhoneDto get(@PathVariable("phoneId") UUID phoneId) {
        return phoneService.get(phoneId);
    }

    @GetMapping()
    public List<PhoneDto> findAll() {
        return phoneService.findAll();
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


    //add endpoint to initiate a phone number verification

    //add endpoint to validate taht phone number verification

}
