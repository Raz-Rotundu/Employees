package com.lumius.auth_microservice.service;

import com.lumius.auth_microservice.enumerable.UserType;

public class ScopeServiceImpl implements ScopeService {

	@Override
	public String getScope(UserType userType) {
		
		switch(userType) {
			case UserType.READER:
				return "employees_table:read";
			case UserType.EDITOR:
				return "employees_table:write";
				
			// TODO Write new InvalidUserType exception and throw it here
			default:
				return "INVALID USER TYPE";
		}
	}

}
