package com.lumius.employees.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lumius.employees.dto.EmployeeDto;
import com.lumius.employees.service.EmployeeService;

import jakarta.validation.Valid;

/**
 * A CRUD controller for an employees database
 */
@RestController
@Validated
@RequestMapping("/api/v1/employees")
public class EmployeeControllerV1 {
	
	EmployeeService service;
	
	public EmployeeControllerV1(
			@Qualifier("jdbcImpl") EmployeeService service) {
				this.service = service;
			}
	

	// Create 
	@PostMapping(
			consumes = "application/json",
			produces = "application/json")
	@PreAuthorize("hasAuthority('SCOPE_employees_table:write')")
	public ResponseEntity<EmployeeDto> createEmployee(
			@RequestBody EmployeeDto newEmployee) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(service.saveEmployee(newEmployee));
	}
	
	// Read
	@GetMapping(
			value = "/{id}",
			produces = "application/json")
	@PreAuthorize("hasAuthority('SCOPE_employees_table:read')")
	public ResponseEntity<EmployeeDto> getEmployee(
			@PathVariable("id") UUID id) {
		return service.getEmployeeByID(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
	}
	
	@GetMapping(
			produces = "application/json")
	@PreAuthorize("hasAuthority('SCOPE_employees_table:read')")
	public ResponseEntity<Page<EmployeeDto>> getAllEmployees(
			@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(service.getAllEmployees(pageNum, pageSize));
	}

	// Update
	@PutMapping(
			value = "/{id}",
			consumes = "application/json",
			produces = "application/json")
	@PreAuthorize("hasAuthority('SCOPE_employees_table:write')")
	public ResponseEntity<EmployeeDto> updateEmployee(
			@PathVariable("id") UUID id,
			@RequestBody @Valid EmployeeDto updatedEmployee) {
		return service.updateEmployee(id, updatedEmployee)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
	}
	
	@PatchMapping(
			value = "/{id}",
			consumes = "application/json",
			produces = "application/json")
	@PreAuthorize("hasAuthority('SCOPE_employees_table:write')")
	public ResponseEntity<EmployeeDto> updateEmployeeFields(
			@PathVariable("id") UUID id,
			@RequestBody EmployeeDto newEmployee) {
		return service.updateEmployeeFields(id, newEmployee)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
	}

	// Delete
	@DeleteMapping(
			value = "/{id}")
	@PreAuthorize("hasAuthority('SCOPE_employees_table:write')")
	public ResponseEntity<Void> deleteEmployee(
			@PathVariable("id") UUID id,
			Authentication authentication) {
		
		Jwt jwt = (Jwt) authentication.getPrincipal();
		String userId = jwt.getClaim("sub");
		
		return service.deleteEmployeeById(id, userId)
				.map(opt -> 
				ResponseEntity.noContent()
					.<Void>build())
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
	}
	
}
