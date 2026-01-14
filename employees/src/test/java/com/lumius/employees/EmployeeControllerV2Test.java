package com.lumius.employees;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.lumius.employees.dto.EmployeeDto;
import com.lumius.employees.service.EmployeeService;

import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Razvan
 * This is the test suite for the HATEOAS controller class
 * Validates the CRUD operations for the controller as well as the ControllerAdvice
 */
@SpringBootTest
public class EmployeeControllerV2Test {
	
	private static final String DOMAIN = "localhost";

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
	
	@MockitoSpyBean
	@Qualifier("jpaImpl")
	EmployeeService jpaServiceSpy;
	
	// Setup
	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApp)
				.build();
		dto = buildDto();
		
		loadDto(dto);
	}
	
	// Controller Advice
	@Test
	public void testControllerAdvice() throws Exception {
		
		// Mockito setup
		doThrow(new RuntimeException("Mockito'd a runtime exception"))
		.when(jpaServiceSpy)
		.getEmployeeByID(any());
		
		// MockMvc Test
		mockMvc.perform(
				get("/api/v2/employees/{id}", dto.getBusinessEntityID())
				.contentType("application/json"))
			.andExpect(status().is5xxServerError());
		
		// Checking
		Mockito.verify(jpaServiceSpy,
				times(1)).getEmployeeByID(any());
	}
	
	
	// Create
	@Test
	public void testCreateEmployee() throws Exception { 
		mockMvc.perform(
				post("/api/v2/employees")
				.contentType("application/json")
				.content(mapper.writeValueAsString(buildDto())))
			.andExpect(status().isCreated());
	}
	
	// Read
	@Test
	public void testGetEmployeeById() throws Exception {
		
		mockMvc.perform(
				get("/api/v2/employees/{id}", dto.getBusinessEntityID())
				.contentType("application/json"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._links.self.href")
					.value("http://" + DOMAIN + "/api/v2/employees/" + dto.getBusinessEntityID()))
			.andExpect(jsonPath("$.employeeDto.businessEntityID")
					.value(dto.getBusinessEntityID().toString()));
		
	}
	
	@Test
	public void testGetAllEmployees() throws Exception {
		
		mockMvc.perform(
				get("/api/v2/employees")
				.contentType("application/json"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._links.allProperties.href")
					.value("http://" + DOMAIN + "/api/v2/employees"))
			.andExpect(jsonPath("$.employeeCollectionDescriptor"
					+ ".pageable"
					+ ".pageNumber")
					.value(1))
			.andExpect(jsonPath("$.employeeCollectionDescriptor"
					+ ".pageable"
					+ ".pageSize")
					.value(10));
					
		
	}
	
	// Update
	@Test
	public void testUpdateEmployee() throws Exception {
		UUID id = dto.getBusinessEntityID();
		
		EmployeeDto newDto = buildDto(id, "changedUser");
		mockMvc.perform(
				put("/api/v2/employees/{id}", id)
				.contentType("application/json")
				.content(mapper.writeValueAsString(newDto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._links.self.href")
					.value("http://" + DOMAIN + "/api/v2/employees/" + id))
			.andExpect(jsonPath("$.employeeDto.businessEntityID")
					.value(id.toString()))
			.andExpect(jsonPath("$.employeeDto.loginID")
					.value("changedUser"));
		
	}
	
	@Test
	public void testUpdateEmployeeFields() throws Exception {
		UUID id = dto.getBusinessEntityID();
		
		EmployeeDto partialDto = buildPartialDto(id, "patchedUser");
		
		mockMvc.perform(
				patch("/api/v2/employees/{id}", id)
				.contentType("application/json")
				.content(mapper.writeValueAsString(partialDto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._links.self.href")
					.value("http://" + DOMAIN + "/api/v2/employees/" + id))
			.andExpect(jsonPath("$.employeeDto.loginID")
					.value("patchedUser"));
	}
	
	// Delete
	@Test
	public void testDeleteEmployeeById() throws Exception {
		UUID id  = dto.getBusinessEntityID();
		// Delete output
		mockMvc.perform(
				delete("/api/v2/employees/{id}", id))
			.andExpect(status().isNoContent());
		
		// Check it doesn't still exist
		mockMvc.perform(
				get("/api/v2/employees/{id}", id)
				.contentType("application/json"))
			.andExpect(status().isNotFound());
	}
	
	
	/**
	 * Helper function to pre-load an EmployeeDto into repository
	 * @param employee EmplpyeeDTO to be loaded
	 * @throws Exception
	 */
	private void loadDto(EmployeeDto dto) throws Exception{
		mockMvc.perform(
				post("/api/v2/employees")
				.contentType("application/json")
				.content(mapper.writeValueAsString(dto)));
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
	private EmployeeDto buildPartialDto(UUID id, String login) {
		return EmployeeDto.builder()
				.businessEntityID(id)
				.loginID(login)
				.build();
	}
	
	
}
