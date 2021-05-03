package ca.devpro.controller;

import ca.devpro.api.PhoneDto;
import ca.devpro.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/Phones")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneDto create(@RequestBody PhoneDto dto) {
        return phoneService.create(dto);
    }

    @GetMapping()
    public List<PhoneDto> findAll() {
        return phoneService.findAll();
    }

    @GetMapping("/{PhoneId}")
    public PhoneDto get(@PathVariable("PhoneId") UUID PhoneId) {
        return phoneService.get(PhoneId);
    }

    @PutMapping("/{PhoneId}")
    public PhoneDto update(@PathVariable("PhoneId") UUID PhoneId, @RequestBody PhoneDto dto) {
        dto.setPhoneId(PhoneId);
        return phoneService.update(dto);
    }

    @DeleteMapping("/{PhoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("PhoneId") UUID PhoneId) {
        phoneService.delete(PhoneId);
    }
}
