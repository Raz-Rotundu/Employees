package com.lumius.auth_microservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lumius.auth_microservice.dto.TokenRequest;
import com.lumius.auth_microservice.dto.TokenResponse;
import com.lumius.auth_microservice.dto.UserDto;
import com.lumius.auth_microservice.service.ClientService;
import com.lumius.auth_microservice.service.JWTService;
import com.lumius.auth_microservice.service.ScopeService;
import com.lumius.auth_microservice.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private JWTService jwtService;
	private UserService userService;
	private ScopeService scopeService;
	private ClientService clientService;

	public UserController(JWTService jwtService,
			UserService userService,
			ScopeService scopeService,
			ClientService clientService) {
		
		this.jwtService = jwtService;
		this.userService = userService;
		this.scopeService = scopeService;
		this.clientService = clientService;
	}
	
	// Creating users
	@PostMapping(value = "/register")
	public ResponseEntity<UserDto> createUser(
			@RequestBody UserDto userDto) {
		
		return userService.createUser(userDto.getEmail(), userDto.getPassword(), userDto.getUserType())
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.badRequest().build());
	}
	
	// Authorizing users
	@PostMapping(value = "/token",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<TokenResponse> createToken(
			@RequestBody TokenRequest tokenRequest) {
		
		// Wrong token type
		if(!tokenRequest.grant_type().equals("password")) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(null);
		}
		
		// Unauthorized client
		if(!clientService.validateClient(tokenRequest.client_id(), tokenRequest.client_secret())) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(null);
		}
		
		//ERROR HERE
		if(!userService.validateUser(tokenRequest.username(), tokenRequest.password())) {
			return ResponseEntity.
					status(HttpStatus.UNAUTHORIZED)
					.body(null);
		}
		
		UserDto foundUser = userService.findUserByEmail(tokenRequest.username()).get();
		
		return ResponseEntity.ok(jwtService.getJWTToken(tokenRequest,
				scopeService.getScope(foundUser.getUserType()), 
				foundUser.getEmail()));
	}
}
