package com.lumius.auth_microservice.service;

import org.springframework.beans.factory.annotation.Value;

public class ClientServiceImpl implements ClientService {
	
	
	@Value("${client.id}")
	private String clientId;
	
	@Value("${client.secret")
	private String clientSecret;

	@Override
	public boolean validateClient(String clientId, String clientSecret) {
		return this.clientId == clientId
				&& this.clientSecret == clientSecret;
	}

}
