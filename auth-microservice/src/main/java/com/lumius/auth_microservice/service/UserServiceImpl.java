package com.lumius.auth_microservice.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.lumius.auth_microservice.dto.UserDto;
import com.lumius.auth_microservice.dto.utils.UserConverter;
import com.lumius.auth_microservice.entity.UserEntity;
import com.lumius.auth_microservice.enumerable.UserType;
import com.lumius.auth_microservice.repository.UserRepository;

public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	
	
	public UserServiceImpl(PasswordEncoder passwordEncoder, 
			UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}
	
	
	@Override
	public Optional<UserDto> createUser(String email, String password, UserType userType) {
		
		if(!userRepository.findUserByEmail(email).isEmpty()) {
			return Optional.empty();
		}
		
		UserEntity newUser = new UserEntity(
				UUID.randomUUID(),
				email, 
				passwordEncoder.encode(password), 
				userType);
		
		return Optional.of(userRepository.save(newUser))
				.map(UserConverter::convertToUserDto);
	}

	@Override
	public Optional<UserDto> findUserByEmail(String email) {
		
		return userRepository.findUserByEmail(email)
				.map(UserConverter::convertToUserDto);
	}

	@Override
	public boolean validateUser(String email, String password) {
		return userRepository.findUserByEmail(email)
				.map(user -> 
					passwordEncoder.matches(password, user.getPassword()))
				.orElse(false);
	}

}
