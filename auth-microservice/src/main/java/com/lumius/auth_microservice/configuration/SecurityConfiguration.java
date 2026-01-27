package com.lumius.auth_microservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * @author Razvan
 * Class contains methods for hashing passwords and configuring DefaultSecurityFilterChain
 */
@Configuration
public class SecurityConfiguration {
	
	@Bean
	PasswordEncoder passwordEncode() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DefaultSecurityFilterChain configure(HttpSecurity http) {
		return http
				
				// CORS and CSRF disabled
				.cors(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				
				// /users /register and /token endpoints visible and accessible to all
				.authorizeHttpRequests(auth -> auth.requestMatchers(
						"/users/register", 
						"/users/token").permitAll()
						.anyRequest()
						.authenticated()
						)
				
				// Session type set to stateless
				.sessionManagement(sess -> sess.sessionCreationPolicy(
						SessionCreationPolicy.STATELESS))
				.build();
	}

}
