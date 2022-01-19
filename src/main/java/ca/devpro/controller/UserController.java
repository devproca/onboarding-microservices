package ca.devpro.controller;

import ca.devpro.api.UserDto;
import ca.devpro.core.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("")
    public List<UserDto> find() {
        return service.find();
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable UUID userId) {
        return service.get(userId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto dto) {
        return service.save(dto);
    }
}
