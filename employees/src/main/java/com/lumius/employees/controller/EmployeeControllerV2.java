package com.lumius.employees.controller;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lumius.employees.controller.utils.EmployeeHyperMediaUtils;
import com.lumius.employees.dto.EmployeeDto;
import com.lumius.employees.dto.descriptors.EmployeeCollectionDescriptor;
import com.lumius.employees.dto.descriptors.EmployeeDescriptor;
import com.lumius.employees.service.EmployeeService;

/**
 * @author Razvan
 * A CRUD controller for an employee database, using HATEOS
 */

@RestController
@RequestMapping("/api/v2/employees")
@Validated
public class EmployeeControllerV2 {
	
	EmployeeService service;
	EmployeeHyperMediaUtils utils;
	
	public EmployeeControllerV2(
			@Qualifier("jpaImpl") EmployeeService service ,
			EmployeeHyperMediaUtils utils) {
		
		this.service = service;
		this.utils = utils;
	}
	
	//Create
	@PostMapping(
			produces = "application/json",
			consumes = "application/json")
	public ResponseEntity<EmployeeDescriptor> createEmployee(
			@RequestBody EmployeeDto newEmployee) {
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(utils.describeEmployeeDto(
						service.saveEmployee(newEmployee)));
		
	}
	
	
	//Read
	@GetMapping(
			value = "/{id}",
			produces = "application/json")
	public ResponseEntity<EmployeeDescriptor> getEmployeeById(
			@PathVariable("id") UUID id) {
		
		return  service.getEmployeeByID(id)
				.map(utils::describeEmployeeDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
		
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<EmployeeCollectionDescriptor> getAllEmployees(
			@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
		
		return Optional.ofNullable(
				utils.describeEmployeePage(
						service.getAllEmployees(pageNumber, pageSize)))
				.map(described -> 
						ResponseEntity.ok().body(described))
				.orElse(ResponseEntity.noContent().build());
		
	}
	
	//Update
	@PutMapping(
			value = "/{id}",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<EmployeeDescriptor> updateEmployee(
			@PathVariable("id") UUID id,
			@RequestBody EmployeeDto newEmployee) {
		
		return service.updateEmployee(id, newEmployee)
				.map(utils::describeEmployeeDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
		
	}
	
	@PatchMapping(
			value = "/{id}",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<EmployeeDescriptor> updateEmployeeFields(
			@PathVariable("id") UUID id,
			@RequestBody EmployeeDto dto) {
		
		return service.updateEmployeeFields(id, dto)
				.map(utils::describeEmployeeDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
	}
	
	//Delete
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteEmployeeById(
			@PathVariable("id") UUID id) {
		
		return service.deleteEmployeeById(id)
				.map(utils::describeEmployeeDto)
				.map(opt -> ResponseEntity.noContent()
					.<Void>build())
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
		
		
	}
}
