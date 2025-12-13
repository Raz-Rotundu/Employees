package com.lumius.employees.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.lumius.employees.dto.EmployeeDto;

@Qualifier("JDBC implementation")
@Service
public class EmployeeServiceJDBCImpl implements EmployeeService {

	public EmployeeServiceJDBCImpl() {
		
	}
	
	@Override
	public EmployeeDto saveEmployee(EmployeeDto newEmployee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<EmployeeDto> getEmployeeByID(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Page<EmployeeDto> getAllEmployees(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<EmployeeDto> updateEmployee(UUID id, EmployeeDto updatedEmployee) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<EmployeeDto> updateEmployeeFields(UUID id, EmployeeDto partialEmployee) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<EmployeeDto> deleteEmployeeById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
