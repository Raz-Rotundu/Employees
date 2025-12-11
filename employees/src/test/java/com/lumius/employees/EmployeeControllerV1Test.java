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
		
		dto = buildDto();
		
		loadDto(dto);
		
		
		
	}
	
	
	// Posts the dto and tests the POST function all in one
	@Test
	public void testCreateEmployee() throws Exception {
		mockMvc.perform(
				post("/api/v1/employees")
				.contentType("Application/json")
				.content(mapper.writeValueAsString(dto)))
			.andExpect(status().isCreated());		
	}
	
	// GET single test
	@Test
	public void testGetEmployee() throws Exception {
		mockMvc.perform(
				get("/api/v1/employees/{id}", dto.getBusinessEntityID())
				.contentType("application/json"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.loginID")
				.value("rrotundu"));
	}
	
	// GET all test
	@Test
	public void testGetAllEmployees() throws Exception {
		mockMvc.perform(
				get("/api/v1/employees")
				.contentType("application/json"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].loginID")
				.value("rrotundu"));
		
	}
	
	// PUT test
	@Test 
	public void testUpdateEmployee() throws Exception{
		EmployeeDto update = buildDto(dto.getBusinessEntityID(), "testLogin");
		
		
		mockMvc.perform(
				put("/api/v1/employees/{id}", dto.getBusinessEntityID())
				.contentType("application/json")
				.content(mapper.writeValueAsString(update)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.loginID")
				.value(update.getLoginID()));
	}
	
	// PATCH
	@Test
	public void testUpdateEmployeeFields() throws Exception {
		EmployeeDto partial = buildPartial(dto.getBusinessEntityID(), "testLogin");
		
		mockMvc.perform(
				patch("/api/v1/employees/{id}", dto.getBusinessEntityID())
				.contentType("application/json")
				.content(mapper.writeValueAsString(partial)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.loginID")
				.value(partial.getLoginID()))
		.andExpect(jsonPath("$.jobTitle")
				.value(dto.getJobTitle()));
		
		
	}
	
	// DELETE Test
	@Test
	public void testDeleteEmployee() throws Exception {
		mockMvc.perform(
				delete("/api/v1/employees/{id}", dto.getBusinessEntityID())
				.contentType("application/json"))
		.andExpect(status().isNoContent());
		
//		mockMvc.perform(
//				get("/api/v1/employees/{id}", dto.getBusinessEntityID())
//				.contentType("application/json"))
//		.andExpect(status().isNotFound());

	}
	
	
	
	
	
	
	// Helper function to preload a Dto into the repository
	private void loadDto(EmployeeDto employee) throws Exception {
		mockMvc.perform(
				post("/api/v1/employees")
				.contentType("application/json")
				.content(mapper.writeValueAsString(employee)));
	}
	
	/**
	 * Create a default EmployeeDto with all fields filled
	 * @return
	 */
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
	
	/**
	 * Create an EmployeeDto with specified loginId and businessEntityId
	 * @param id set businessEntityID
	 * @param login set loginID
	 * @return an EmployeeDto with specified loginId and businessEntityId, other fields filled with default values
	 */
	private EmployeeDto buildDto(UUID id, String login) {
		return EmployeeDto.builder()
				.businessEntityID(id)
				.nationalIDNumber("01")
				.loginID(login)
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
	
	/**
	 * Create a partially complete EmployeeDto object, with only businessEntityID and loginID set
	 * @param id the businessEntityID
	 * @param login the loginID
	 * @return a partially complete EmployeeDto object, with only businessEntityID and loginID set
	 */
	private EmployeeDto buildPartial(UUID id, String login) {
		return EmployeeDto.builder()
				.businessEntityID(id)
				.loginID(login)
				.build();
	}
	
}
