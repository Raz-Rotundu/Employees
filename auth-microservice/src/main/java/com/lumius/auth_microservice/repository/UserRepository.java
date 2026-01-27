package com.lumius.auth_microservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lumius.auth_microservice.entity.UserEntity;

/**
 * @author Razvan
 * JPA repository interface for Users
 */
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

	/**
	 * Retrieves users by email address instead of ID
	 * @param email the unique email address of the users
	 * @return a UserEntity with the given email or null
	 */
	public Optional<UserEntity> findUserByEmail(String email);
}
