package com.lumius.employees;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.lumius.employees.dto.EmployeeDto;

import tools.jackson.databind.ObjectMapper;

/**
 * @author Razvan
 * This it the test suite for the non-HATEOAS controller class. 
 * Validates the CRUD operations of the controller
 */

@SpringBootTest
public class EmployeeControllerV1Test {
	
	WebApplicationContext app;
	ObjectMapper mapper;
	
	MockMvc mockMvc;
	EmployeeDto dto;
	
	
}
