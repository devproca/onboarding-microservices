package ca.devpro.core.service;

import ca.devpro.api.UserDto;
import ca.devpro.core.assembler.UserAssembler;
import ca.devpro.core.entity.UserEntity;
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

	public UserDto createUser(UserDto userDto){
		//TODO add validation
		UserEntity entity = userAssembler.disassembleInto(userDto, UserEntity.newInstance());
		entity = userRepository.save(entity);
		return userAssembler.assemble(entity);
	}

	//TODO: Create a update function to update a user's username only

	//TODO: Create a delete function
	//use userRepository.delete(...id)
}
