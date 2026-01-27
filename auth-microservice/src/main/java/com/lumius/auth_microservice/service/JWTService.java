package com.lumius.auth_microservice.service;

import com.lumius.auth_microservice.dto.TokenRequest;
import com.lumius.auth_microservice.dto.TokenResponse;

/**
 * @author Razvan
 * Handles creating JWT tokens
 */
public interface JWTService {

	/**
	 * Create JWT token from the request, scope and userId info provided
	 * @param request The request for the token
	 * @param scope The scope the token will have
	 * @param userId The Id with which to issue the token (User email in this case)
	 * @return A tokenResponse containing valid token
	 */
	public TokenResponse getJWTToken(TokenRequest request, String scope, String userId);
}
