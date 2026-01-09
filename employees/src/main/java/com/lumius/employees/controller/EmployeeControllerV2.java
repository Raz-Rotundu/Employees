package com.lumius.employees.controller;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
			@Qualifier("JpaImpl") EmployeeService service ,
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
			@PathVariable UUID id) {
		
		return  service.getEmployeeByID(id)
				.map(utils::describeEmployeeDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null));
		
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<EmployeeCollectionDescriptor> getAllEmployees(
			@RequestParam int pageNumber,
			@RequestParam int pageSize) {
		
		throw new UnsupportedOperationException("TODO");
	}
	
	//Update
	
	//Delete
	
	public ResponseEntity<Void> deleteEmployeeById() {
		
		throw new UnsupportedOperationException("TODO");
	}
}
