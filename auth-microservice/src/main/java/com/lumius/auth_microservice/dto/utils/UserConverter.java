package com.lumius.auth_microservice.dto.utils;

import org.springframework.stereotype.Component;

import com.lumius.auth_microservice.dto.UserDto;
import com.lumius.auth_microservice.entity.UserEntity;

/**
 * @author Razvan
 * Utility class to convert between UserEntity and UserDto
 */
@Component
public class UserConverter {

	/**
	 * User DTO to Entity
	 * @param dto the UserDTO to be converted
	 * @return an Entity equivalent of given DTO
	 */
	public static UserEntity convertUserEntity(UserDto dto) {
		return UserEntity.builder()
				.email(dto.getEmail())
				.password(dto.getPassword()) //TODO use the password hasher
				.userType(dto.getUserType())
				.build();
	}
	
	/**
	 * User Entity to DTO
	 * @param entity the UserEntity to be converted
	 * @return a DTO equivalent of the given Entity
	 */
	public static UserDto convertToUserDto(UserEntity entity) {
		return UserDto.builder()
				.email(entity.getEmail())
				.password(entity.getPassword())
				.userType(entity.getUserType())
				.build();
	}
}
