package com.lumius.auth_microservice.dto;

import java.util.UUID;

import com.lumius.auth_microservice.enumerable.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Razvan
 * DTO representing a user in the user database
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
	UUID id;
	String email;
	String password;
	UserType userType;
}
