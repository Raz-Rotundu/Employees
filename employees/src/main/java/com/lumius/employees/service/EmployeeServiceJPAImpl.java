package com.lumius.employees.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lumius.employees.dto.EmployeeDto;
import com.lumius.employees.dto.utils.EmployeeConverter;
import com.lumius.employees.repositories.EmployeeRepository;

@Service
@Qualifier("JPAImpl")
public class EmployeeServiceJPAImpl implements EmployeeService {

	EmployeeRepository repository;
	
	public EmployeeServiceJPAImpl(EmployeeRepository repository) {
		this.repository = repository;
	}
	
	
	@Override
	public EmployeeDto saveEmployee(EmployeeDto newEmployee) {

		return EmployeeConverter.toDTO(
				repository.save(
						EmployeeConverter.toEntity(newEmployee)));
	}

	
	@Override
	public Optional<EmployeeDto> getEmployeeByID(UUID id) {

		return repository.findById(id)
				.map(EmployeeConverter::toDTO);
	}

	
	@Override
	public List<EmployeeDto> getAllEmployees() {
		return repository.findAll()
				.stream()
				.map(EmployeeConverter::toDTO)
				.toList();
	}

	@Override
	public Optional<EmployeeDto> updateEmployee(UUID id, EmployeeDto updatedEmployee) {
		if(repository.existsById(id)) {
			return Optional.ofNullable(
					EmployeeConverter.toDTO(
							repository.save(
									EmployeeConverter.toEntity(updatedEmployee))));
					
		} else {
			return Optional.empty();
		}
	}

	// Check each field of the existing, and replace it if its present in the updated
	@Override
	public Optional<EmployeeDto> updateEmployeeFields(UUID id, EmployeeDto newEmployee) {
		return repository.findById(id)
				.map(EmployeeConverter::toDTO)
			.map(original -> EmployeeDto.builder()
					// Unchanged
					.businessEntityID(id)
					
					.nationalIDNumber(nullOrEmpty(newEmployee.getNationalIDNumber() ) ? original.getNationalIDNumber() : newEmployee.getNationalIDNumber() )
					
					.loginID(nullOrEmpty(newEmployee.getLoginID() ) ? newEmployee.getLoginID() : original.getLoginID() )
					
					.organizationNode(nullOrEmpty(newEmployee.getOrganizationNode() ) ? original.getOrganizationNode() : newEmployee.getOrganizationNode() )
					
					.organizationLevel(nullOrEmpty(newEmployee.getOrganizationLevel() ) ? original.getOrganizationLevel() : newEmployee.getOrganizationLevel())
					
					.jobTitle(nullOrEmpty(newEmployee.getJobTitle() ) ? original.getJobTitle() : newEmployee.getJobTitle() )
					
					.birthDate(nullOrEmpty(newEmployee.getBirthDate() ) ? original.getBirthDate() : newEmployee.getBirthDate() )
					
					.maritalStatus(nullOrEmpty(newEmployee.getMaritalStatus() ) ? original.getMaritalStatus() : newEmployee.getMaritalStatus())
					
					.gender(nullOrEmpty(newEmployee.getGender() ) ? original.getGender() : newEmployee.getGender() )
					
					.hireDate(nullOrEmpty(newEmployee.getHireDate() ) ? original.getHireDate() : newEmployee.getHireDate() )
					
					.salariedFlag(nullOrEmpty(newEmployee.getSalariedFlag() ) ? original.getSalariedFlag() : newEmployee.getSalariedFlag() )
					
					.vacationHours(nullOrEmpty(newEmployee.getVacationHours() ) ? original.getVacationHours(): newEmployee.getVacationHours() )
					
					.sickLeaveHours(nullOrEmpty(newEmployee.getSickLeaveHours() ) ? original.getSickLeaveHours(): newEmployee.getSickLeaveHours() )
					
					.currentFlag(nullOrEmpty(newEmployee.getCurrentFlag() ) ? original.getCurrentFlag() : newEmployee.getCurrentFlag() )
					
					.rowGuid(nullOrEmpty(newEmployee.getRowGuid() ) ? original.getRowGuid() : newEmployee.getRowGuid() )
					
					.modifiedDate(nullOrEmpty(newEmployee.getModifiedDate() ) ? original.getModifiedDate() : newEmployee.getModifiedDate() )
					
					.build())
			.map(EmployeeConverter::toEntity)
			.map(repository::save)
			.map(EmployeeConverter::toDTO);
		
	}

	@Override
	public Optional<EmployeeDto> deleteEmployeeById(UUID id) {
		Optional<EmployeeDto> employeeOptional = repository.findById(id)
				.map(EmployeeConverter::toDTO);
		
		repository.deleteById(id);
		return employeeOptional;
	}

	/**
	 * A helper function to updateEmployeeFields
	 * Determines if a given object is null or empty
	 * @param address a potentially empty or null object
	 * @return true or false if an object is null or empty
	 */
	private boolean nullOrEmpty(Object address) {
		if(address == null) return true;
		if(address instanceof String) return ((String)address).isEmpty();
		return false;
	}
	
}
