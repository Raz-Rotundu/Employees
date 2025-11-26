package com.lumius.employees;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeesApplication.class, args);
		
		SpringApplication app = new SpringApplication(EmployeesApplication.class);
		
		configureH2(app);
		
		app.run(args);
	}

	
	public static void configureH2(SpringApplication app) {
		app.setDefaultProperties(
				Map.of(
					"spring.datasource.url",
						"jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
						
					"spring.datasource.driverClassName",
						"org.h2.Driver",
						
					"spring.datasource.username",
						"user",
						
					"spring.datasource.password",
						"pass",
						
					"spring.jpa.database-platform",
						"org.hibernate.dialect.H2Dialect",
						
					"spring.h2.console.enabled",
						"true",
						
					"spring.h2.console.path",
						"/h2-console",
						
					// Creates new table matching DTO
					"spring.jpa.hibernate.ddl-auto",
						"create",
						
					"spring.jpa.show-sql",
						"true"));
	}
}
