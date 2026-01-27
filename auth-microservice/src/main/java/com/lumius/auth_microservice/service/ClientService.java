package com.lumius.auth_microservice.service;

/**
 * @author Razvan
 * Validates clients by comparing their credentials to list of approved credentials
 */
public interface ClientService {

	/**
	 * Validates client based on its provided credentials
	 * @param clientId the client's public id
	 * @param clientSecret the client's secret key
	 * @return true if valid, false otherwise
	 */
	public boolean validateClient(String clientId, String clientSecret);
}
