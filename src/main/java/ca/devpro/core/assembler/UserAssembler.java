package ca.devpro.core.assembler;


import ca.devpro.api.UserDto;
import ca.devpro.core.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {
	public UserDto assemble(UserEntity userEntity){
		return new UserDto()
				.setUserId(userEntity.getUserId())
				.setUsername(userEntity.getUsername());
	}


	public UserEntity disassembleInto(UserDto userDto, UserEntity entity){
		return entity
				.setUsername(userDto.getUsername());
	}
}
