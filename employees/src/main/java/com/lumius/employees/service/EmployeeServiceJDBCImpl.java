package com.lumius.employees.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.lumius.employees.dto.EmployeeDto;

@Qualifier("JdbcImpl")
@Service
public class EmployeeServiceJDBCImpl implements EmployeeService {
	
	private static final String TABLE_NAME = "employees_table";
	
	private NamedParameterJdbcTemplate template;
	private EmployeeRowMapper rowMapper;

	public EmployeeServiceJDBCImpl(
			NamedParameterJdbcTemplate template,
			EmployeeRowMapper rowMapper) {
		this.template = template;
		this.rowMapper = rowMapper;	
	}
	
	@Override
	public EmployeeDto saveEmployee(EmployeeDto newEmployee) {
		StringBuilder query = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		writeDtoQuery(query);	
		loadDtoParams(params, newEmployee);
		
		template.update(query.toString(), params);
		
		return newEmployee;
	}

	@Override
	public Optional<EmployeeDto> getEmployeeByID(UUID id) {
		StringBuilder query = new StringBuilder();
		
		
		query.append("SELECT * FROM " + TABLE_NAME + " ");
		query.append("WHERE businessEntityID EQUALS " + id +";");
		
		return Optional.ofNullable(
				template.query(query.toString(), rowMapper)
				.get(1));
	}

	@Override
	public Page<EmployeeDto> getAllEmployees(int pageNum, int pageSize) {
		PageRequest page = PageRequest.of(pageNum, pageSize);
		StringBuilder query = new StringBuilder();
		
		return PageableExecutionUtils.getPage(
				template.query(query.toString(), rowMapper), page, null);
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

	
	// Helper function to set up an INSERT with all the DTO column names
	private void writeDtoQuery(StringBuilder query) {
		query.append("INSERT INTO " + TABLE_NAME + " ");
		query.append("(business_entityid, nationalidnumber, loginid, "
				+ "organization_node, organization_level, "
				+ "job_title, birth_date, marital_status, gender, hire_date, "
				+ "salaried_flag, vacation_hours, sick_leave_hours, current_flag, row_guid, modified_date) ");
		
		query.append("VALUES ");
		query.append("(:businessEntityID, :nationalIDNumber, :loginID, "
				+ ":organizationNode, :organizationLevel ,"
				+ ":jobTitle, :birthDate, :maritalStatus, :gender, :hireDate, "
				+ ":salariedFlag, :vacationHours, :sickLeaveHours, :currentFlag, :rowGuid, :modifiedDate);");
		
	}
	// Helper function to quickly load up the DTO field names as parameter names
	private void loadDtoParams(MapSqlParameterSource param, EmployeeDto dto) {
		param.addValue("businessEntityID", dto.getBusinessEntityID());
		param.addValue("nationalIDNumber", dto.getNationalIDNumber());
		param.addValue("loginID", dto.getLoginID());
		param.addValue("organizationNode", "organizationNode");
		param.addValue("organizationLevel", dto.getOrganizationLevel());
		param.addValue("jobTitle", dto.getJobTitle());
		param.addValue("birthDate", dto.getBirthDate());
		param.addValue("maritalStatus", dto.getMaritalStatus());
		param.addValue("gender", dto.getGender());
		param.addValue("hireDate", dto.getHireDate());
		param.addValue("salariedFlag",  dto.getSalariedFlag());
		param.addValue("vacationHours", dto.getVacationHours());
		param.addValue("sickLeaveHours", dto.getSickLeaveHours());
		param.addValue("currentFlag", dto.getCurrentFlag());
		param.addValue("rowGuid", dto.getRowGuid());
		param.addValue("modifiedDate", dto.getModifiedDate());
	
	}
}
