package com.lumius.employees.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lumius.employees.dto.EmployeeDTO;
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
	public EmployeeDTO saveEmployee(EmployeeDTO newEmployee) {

		return EmployeeConverter.toDTO(
				repository.save(
						EmployeeConverter.toEntity(newEmployee)));
	}

	
	@Override
	public Optional<EmployeeDTO> getEmployeeByID(UUID id) {

		return repository.findById(id)
				.map(EmployeeConverter::toDTO);
	}

	
	@Override
	public List<EmployeeDTO> getAllEmployees() {
		return repository.findAll()
				.stream()
				.map(EmployeeConverter::toDTO)
				.toList();
	}

	@Override
	public Optional<EmployeeDTO> updateEmployee(UUID id, EmployeeDTO updatedEmployee) {
		if(repository.existsById(id)) {
			return Optional.ofNullable(
					EmployeeConverter.toDTO(
							repository.save(
									EmployeeConverter.toEntity(updatedEmployee))));
					
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<EmployeeDTO> updateEmployeeFields(UUID id, EmployeeDTO newEmployee) {
		return repository.findById(id)
				.map(EmployeeConverter::toDTO)
			.map(original -> EmployeeDTO.builder()
					// Unchanged
					.businessEntityID(id)
					.nationalIDNumber(nullOrEmpty(original.getNationalIDNumber() ) ? newEmployee.getNationalIDNumber() : original.getNationalIDNumber() )
					.loginID(nullOrEmpty(original.getNationalIDNumber() ) ? newEmployee.getNationalIDNumber() : original.getNationalIDNumber() )
					.organizationNode(nullOrEmpty(original.getOrganizationNode() ) ? newEmployee.getOrganizationNode() : original.getOrganizationNode() )
					.organizationLevel(nullOrEmpty(original.getOrganizationLevel()) ? newEmployee.getOrganizationLevel() : original.getOrganizationLevel())
					.jobTitle(nullOrEmpty(original.getJobTitle() ) ? newEmployee.getJobTitle() : original.getJobTitle() )
					.birthDate(nullOrEmpty(original.getBirthDate()) ? newEmployee.getBirthDate() : original.getBirthDate() )
					.maritalStatus(nullOrEmpty(original.getMaritalStatus()) ? newEmployee.getMaritalStatus() : original.getMaritalStatus())
					.gender(nullOrEmpty(original.getGender()) ? newEmployee.getGender() : original.getGender() )
					.hireDate(nullOrEmpty(original.getHireDate()) ? newEmployee.getHireDate() : original.getHireDate() )
					.salariedFlag(nullOrEmpty(original.getSalariedFlag()) ? newEmployee.getSalariedFlag() : original.getSalariedFlag() )
					.vacationHours(nullOrEmpty(original.getVacationHours()) ? newEmployee.getVacationHours(): original.getVacationHours() )
					.sickLeaveHours(nullOrEmpty(original.getSickLeaveHours()) ? newEmployee.getSickLeaveHours(): original.getSickLeaveHours() )
					.currentFlag(nullOrEmpty(original.getCurrentFlag()) ? newEmployee.getCurrentFlag() : original.getCurrentFlag() )
					.rowGuid(nullOrEmpty(original.getRowGuid()) ? newEmployee.getRowGuid() : original.getRowGuid() )
					.modifiedDate(nullOrEmpty(original.getModifiedDate()) ? newEmployee.getModifiedDate() : original.getModifiedDate() )
					.build())
			.map(EmployeeConverter::toEntity)
			.map(repository::save)
			.map(EmployeeConverter::toDTO);
		
	}

	@Override
	public Optional<EmployeeDTO> deleteEmployeeById(UUID id) {
		Optional<EmployeeDTO> employeeOptional = repository.findById(id)
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
