package com.lumius.auth_microservice.service;

import com.lumius.auth_microservice.enumerable.UserType;

/**
 * @author Razvan
 * ScopeService is responsible for converting UserTypes into valid JWT scopes
 */
public interface ScopeService {

	/**
	 * Converts given userType into a JWT scope string
	 * @param userType
	 * @return a String representing a valid JWT scope
	 */
	public String getScope(UserType userType);
}
