package com.lumius.employees.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/**
 * @author Razvan
 * This configuration class sets the following properties:
 * Stateless service
 * csrf disabled
 * Any request is authenticated, JWT token is proof enough
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	protected DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
			// CORS and CSRF
			.csrf(AbstractHttpConfigurer::disable)
			
			// Authentication on all requests
			.authorizeHttpRequests(auth -> 
				auth.anyRequest()
					.authenticated())
			
			// Session set to stateless
			.sessionManagement(sess -> 
				sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			
			
			.oauth2ResourceServer(server -> 
					server.jwt(jwt -> 
							jwt.jwtAuthenticationConverter(new JwtAuthenticationConverter())));
		return http.build();
	}
}
