package com.lumius.employees.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lumius.employees.dto.EmployeeDto;

/**
 * @author Razvan 
 * Interface describing the operations which can be done on the Employees table.
 * Supported operations are the basic CRUD operations
 */
public interface EmployeeService {

	// Create
	/**
	 * Saves a new employee to the repository
	 * @param newEmployee the new employee to be saved
	 * @return newly created employee
	 */
	EmployeeDto saveEmployee(EmployeeDto newEmployee);
	
	// Read
	/**
	 * Retrieves existing employee from repository based on ID
	 * @param id the ID of the employee to be retrieved
	 * @return Optional containing employee if found
	 */
	Optional<EmployeeDto> getEmployeeByID(UUID id);
	
	/**
	 * Retrieves all employees present in the repository
	 * @return A list of all present Employees in the repository
	 */
	List<EmployeeDto> getAllEmployees();
	
	// Update
	/**
	 * Update an existing employee, fully replacing the content
	 * @param id ID of an existing employee
	 * @param updatedEmployee Employee with which to replace the existing entry
	 * @return Optional containing updated employee it it exists
	 */
	Optional<EmployeeDto> updateEmployee(UUID id, EmployeeDto updatedEmployee);
	
	/**
	 * Update specific fields of an existing employee
	 * @param id ID of an existing employee
	 * @param partialEmployee A partially constructed employee, with only fields to be updated having values
	 * @return
	 */
	Optional<EmployeeDto> updateEmployeeFields(UUID id, EmployeeDto partialEmployee);
	
	// Delete
	/**
	 * Delete an existing employee from the repository
	 * @param id ID of existing employee
	 * @return Empty Optional 
	 */
	Optional<EmployeeDto> deleteEmployeeById(UUID id);

	
	
}
