package com.lumius.employees.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lumius.employees.dto.EmployeeDTO;
import com.lumius.employees.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/v1/employees")
public class EmployeeControllerV1 {
	
	EmployeeService service;
	
	public EmployeeControllerV1(
			@Qualifier("JPAImpl") EmployeeService service) {
				this.service = service;
			}
	

	// Create 
	@PostMapping(
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<EmployeeDTO> createEmployee(
			@RequestBody @Valid EmployeeDTO newEmployee) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(service.saveEmployee(newEmployee));
	}
	
	// Read
	@GetMapping(
			value = "/{id}",
			produces = "application/json")
	public ResponseEntity<EmployeeDTO> getEmployee(
			@PathVariable("id") UUID id) {
		return service.getEmployeeByID(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
	}
	
	@GetMapping(
			produces = "application/json")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(service.getAllEmployees());
	}
	
	// Update
	@PutMapping(
			value = "/{id}",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<EmployeeDTO> updateEmployee(
			@PathVariable("id") UUID id,
			@RequestBody @Valid EmployeeDTO updatedEmployee) {
		return service.updateEmployee(id, updatedEmployee)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
	}
	
	@PatchMapping(
			value = "/{id}",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<EmployeeDTO> updateEmployeeFields(
			@PathVariable("id") UUID id,
			@RequestBody EmployeeDTO newEmployee) {
		return service.updateEmployeeFields(id, newEmployee)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
	}
	
	// Delete
	@DeleteMapping(
			value = "/{id}")
	public ResponseEntity<Void> deleteEmployee(
			@PathVariable UUID id) {
		return service.deleteEmployeeById(id)
				.map(opt -> 
				ResponseEntity.noContent()
					.<Void>build())
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
	}
}
