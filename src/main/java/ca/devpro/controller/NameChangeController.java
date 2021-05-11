package ca.devpro.controller;

import ca.devpro.api.NameChangeDto;
import ca.devpro.service.NameChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/name-changes")

public class NameChangeController {

    @Autowired
    private NameChangeService nameChangeService;

    @GetMapping()
    public List<NameChangeDto> findAllNameChanges() {
        return nameChangeService.findAll();
    }

    @GetMapping("/{nameChangeId}")
    public NameChangeDto getNameChange(@PathVariable("nameChangeId") UUID nameChangeId) {
        return nameChangeService.get(nameChangeId);
    }
}
