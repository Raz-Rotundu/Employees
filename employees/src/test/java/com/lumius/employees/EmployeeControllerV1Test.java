package com.lumius.employees;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import com.lumius.employees.dto.EmployeeDto;

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Razvan
 * This it the test suite for the non-HATEOAS controller class. 
 * Validates the CRUD operations of the controller
 */

@SpringBootTest
public class EmployeeControllerV1Test {
	
	// Injected
	WebApplicationContext context;
	ObjectMapper mapper;
	
	// Initialized later
	MockMvc mockMvc;
	EmployeeDto dto;
	
	@Autowired
	public EmployeeControllerV1Test(
			WebApplicationContext context,
			ObjectMapper mapper) {
		this.context = context;
		this.mapper = mapper;	
	}
	
	// Reset DTO and mockMVC state
	@BeforeEach
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
		
		dto = createAndPostDto();
		
	}
	
	
	// Posts the dto and tests the POST function all in one
	@Test
	public EmployeeDto createAndPostDto() throws Exception {
		String responseBody = mockMvc.perform(
					post("/api/v1/employees")
					.contentType("Application/json")
					.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isCreated())
				.andReturn().getResponse().getContentAsString();
		
		return mapper.readValue(responseBody, EmployeeDto.class);

	}
	
	// GET single test
	@Test
	public void testGetEmployeeById() throws Exception {
		mockMvc.perform(
				get("/api/v1/employees/{id}", dto.getBusinessEntityID())
				.contentType("Application/json"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.loginID")
				.value("rrotundu"));
	}
	// GET all test
	// PUT test
	// PATCH
	// DELETE Test
	
	// Helper function to create a default DTO object
	private EmployeeDto buildDto() {
		return EmployeeDto.builder()
				.businessEntityID(UUID.randomUUID())
				.nationalIDNumber("01")
				.loginID("rrotundu")
				.jobTitle("Supreme Coder")
				.birthDate(LocalDate.of(1998, 01, 20))
				.maritalStatus("S")
				.gender("M")
				.hireDate(LocalDate.now())
				.salariedFlag("T")
				.vacationHours(100)
				.sickLeaveHours(100)
				.currentFlag("T")
				.rowGuid(UUID.randomUUID())
				.modifiedDate(LocalDateTime.now())
				.build();
	}
	
}
