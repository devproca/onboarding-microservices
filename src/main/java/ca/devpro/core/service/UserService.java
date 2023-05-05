package ca.devpro.core.service;

import ca.devpro.api.UserDto;
import ca.devpro.core.assembler.UserAssembler;
import ca.devpro.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserAssembler userAssembler;

	public List<UserDto> findAll(){
		return userRepository.findAll()
				.stream()
				.map(userAssembler::assemble)
				.collect(Collectors.toList());
	}
}
