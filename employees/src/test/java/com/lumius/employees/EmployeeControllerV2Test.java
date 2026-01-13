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

@SpringBootTest
public class EmployeeControllerV2Test {

	private WebApplicationContext webApp;
	private ObjectMapper mapper;
	
	@Autowired
	public EmployeeControllerV2Test(
			WebApplicationContext webApp,
			ObjectMapper mapper) {
		
		this.webApp = webApp;
		this.mapper = mapper;
	}
	
	private MockMvc mockMvc;
	private EmployeeDto dto;
	
	// Setup
	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApp)
				.build();
		dto = buildDto();
		
		loadDto(dto);
	}
	
	// Create
	@Test
	public void testCreateEmployee() throws Exception{
		mockMvc.perform(
				post("/api/v2/employees")
				.contentType("application/json")
				.content(mapper.writeValueAsString(buildDto())))
			.andExpect(status().isCreated());
	}
	
//	// Read
//	@Test
//	public void testGetEmployeeById() throws Exception {
//		
//	}
//	
//	@Test
//	public void testGetAllEmployees() throws Exception {
//		
//	}
//	
//	// Update
//	@Test
//	public void testUpdateEmployee() throws Exception {
//		
//	}
//	
//	@Test
//	public void testUpdateEmployeeFields() throws Exception {
//		
//	}
//	
//	// Delete
//	@Test
//	public void testDeleteEmployeeById() throws Exception {
//		
//	}
	
	// POSTs a dto into the mvc
	private void loadDto(EmployeeDto dto) throws Exception{
		mockMvc.perform(
				post("/api/v2/employees")
				.contentType("application/json")
				.content(mapper.writeValueAsString(dto)));
	}
	
	// Build a default dto
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
	
	// Build a dto with a custom businessEntityId, loginID
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
	
	// Build a partially complete dto, with only businessEntityId and loginID fields initialized
	private EmployeeDto buildPartialDto(UUID id, String login) {
		return EmployeeDto.builder()
				.businessEntityID(id)
				.loginID(login)
				.build();
	}
	
	
}
