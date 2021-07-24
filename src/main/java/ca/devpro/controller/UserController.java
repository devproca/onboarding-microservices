package ca.devpro.controller;

import ca.devpro.dto.UserDto;
import ca.devpro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto dto) {
        return userService.create(dto);
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable UUID userId) {
        return userService.get(userId);
    }

    @PutMapping("/{userId}")
    public UserDto update(@PathVariable UUID userId, @RequestBody UserDto dto) {
        dto.setUserId(userId);
        System.out.println("Check 1: " + dto.getUserId());
        return userService.update(dto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID userId) {
        userService.delete(userId);
    }

}
