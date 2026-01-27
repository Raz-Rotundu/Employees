package com.lumius.auth_microservice.dto;

/**
 * @author Razvan
 * Record encapsulating information for a token request
 */
public record TokenRequest(
		String grant_type,
		String username,
		String password,
		String client_id,
		String client_secret) 
{}
