package ca.devpro.controller;


import ca.devpro.api.UserDto;
import ca.devpro.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	public List<UserDto> findAll(){
		return userService.findAll();
	}

	@PostMapping()
	public UserDto create(@RequestBody UserDto userDto){
		return userService.createUser(userDto);
	}

	//TODO: PUT for updating user

	//TODO: DELETE

}
