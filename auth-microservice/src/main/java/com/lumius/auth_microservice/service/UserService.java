package com.lumius.auth_microservice.service;

import java.util.Optional;

import com.lumius.auth_microservice.dto.UserDto;
import com.lumius.auth_microservice.enumerable.UserType;

/**
 * @author Razvan
 * Service handles the creation, validation, and fetching of users from repository
 */
public interface UserService {
	
	/**
	 * Create user based on given email, password, userType
	 * @param email
	 * @param password
	 * @param userType
	 * @return A new UserDto or null if process failed
	 */
	public Optional<UserDto> createUser(String email, String password, UserType userType);
	
	
	/**
	 * Retrieve user from repository based on unique email
	 * @param email
	 * @return The user with the given email, or null if not found
	 */
	public Optional<UserDto> findUserByEmail(String email);
	
	
	/**
	 * Validates a user based on given email and password
	 * @param email
	 * @param password
	 * @return True if user with given email exists in repository, false otherwise.
	 */
	public boolean validateUser(String email, String password);
}
