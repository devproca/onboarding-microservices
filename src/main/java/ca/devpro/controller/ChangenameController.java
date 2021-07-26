package ca.devpro.controller;

import ca.devpro.dto.ChangenameDto;
import ca.devpro.service.ChangenameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/changenames")
public class ChangenameController {

    @Autowired
    private ChangenameService changenameService;

    @GetMapping()
    public List<ChangenameDto> findByUserId(@PathVariable("userId") UUID userId) {
        return changenameService.findByUserId(userId);
    }

}