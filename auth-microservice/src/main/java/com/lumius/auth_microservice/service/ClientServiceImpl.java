package com.lumius.auth_microservice.service;

public class ClientServiceImpl implements ClientService {
	
	
	//TODO Setup external config file for valid client IDs and secrets
	private String clientId = "changeThis";
	private String clientSecret = "changeThis";

	@Override
	public boolean validateClient(String clientId, String clientSecret) {
		return this.clientId == clientId
				&& this.clientSecret == clientSecret;
	}

}
