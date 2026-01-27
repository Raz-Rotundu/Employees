package com.lumius.auth_microservice.dto;

/**
 * @author Razvan
 * A record encapsulating the info for a JWT token reply
 * Expires in is seconds
 */
public record TokenReply(
		String access_token,
		String token_type,
		String expires_in,
		String scope) 
{}
