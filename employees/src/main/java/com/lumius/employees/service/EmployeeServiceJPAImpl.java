package com.lumius.employees.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lumius.employees.dto.EmployeeDTO;

@Service
@Qualifier("JPAImpl")
public class EmployeeServiceJPAImpl implements EmployeeService {

	public EmployeeServiceJPAImpl() {
		
	}
	@Override
	public EmployeeDTO saveEmployee(EmployeeDTO newEmployee) {
		// TODO Auto-generated method stub
		return newEmployee;
	}

	@Override
	public Optional<EmployeeDTO> getEmployeeByID(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<EmployeeDTO> getAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<EmployeeDTO> updateEmployee(UUID id, EmployeeDTO updatedEmployee) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<EmployeeDTO> updateEmployeeFields(UUID id, EmployeeDTO partialEmployee) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Void> deleteEmployeeById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
