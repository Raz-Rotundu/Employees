package com.lumius.employees.service;

import java.time.LocalDateTime;
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
					
					.nationalIDNumber(compareNewToOld(newEmployee.getNationalIDNumber(), original.getNationalIDNumber()))
					
					.loginID(compareNewToOld(newEmployee.getLoginID(), original.getLoginID()))
					
					.organizationNode(compareNewToOld(newEmployee.getOrganizationNode(), original.getOrganizationNode()))
					.organizationLevel(compareNewToOld(newEmployee.getOrganizationLevel(), original.getOrganizationLevel()))
					.jobTitle(compareNewToOld(newEmployee.getJobTitle(), original.getJobTitle()))
					.birthDate(compareNewToOld(newEmployee.getBirthDate(), original.getBirthDate()))
					.maritalStatus(compareNewToOld(newEmployee.getMaritalStatus(), original.getMaritalStatus()))
					.gender(compareNewToOld(newEmployee.getGender(), original.getGender()))
					.hireDate(compareNewToOld(newEmployee.getHireDate(), original.getHireDate()))
					.salariedFlag(compareNewToOld(newEmployee.getSalariedFlag(), original.getSalariedFlag()))
					.vacationHours(compareNewToOld(newEmployee.getVacationHours(), original.getVacationHours()))
					.sickLeaveHours(compareNewToOld(newEmployee.getSickLeaveHours(), original.getSickLeaveHours()))
					
					.currentFlag(compareNewToOld(newEmployee.getCurrentFlag(), original.getCurrentFlag()))
					.rowGuid(compareNewToOld(newEmployee.getRowGuid(), original.getRowGuid()))
					
					// Modified date set to current
					.modifiedDate(LocalDateTime.now())
					
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
	
	// Comparison helper
	private <T> T compareNewToOld(T newValue, T oldValue) {
		return nullOrEmpty(newValue)? oldValue : newValue;
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
