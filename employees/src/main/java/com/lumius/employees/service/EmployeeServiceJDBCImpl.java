package com.lumius.employees.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		query.append("SELECT * FROM " + TABLE_NAME + 
				" WHERE business_entityid = :id;");
		
		params.addValue("id", id);

		if(existsById(id)) {
			return Optional.ofNullable(
					template.queryForObject(query.toString(), params, rowMapper));
		} else {
			return Optional.empty();
		}

	}

	@Override
	public Page<EmployeeDto> getAllEmployees(int pageNum, int pageSize) {
		PageRequest page = PageRequest.of(pageNum, pageSize);
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM " + TABLE_NAME + ";");
		
		return PageableExecutionUtils.getPage(
				template.query(query.toString(), rowMapper), page, () -> getTableSize());
	}

	@Override
	public Optional<EmployeeDto> updateEmployee(UUID id, EmployeeDto updatedEmployee) {
		StringBuilder queryBuilder = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		if (existsById(id)) {
			
			writeUpdateQuery(queryBuilder);
			
			
			loadUpdateParams(params, updatedEmployee);
			template.update(queryBuilder.toString(), params);
			
			return getEmployeeByID(id);
			
			
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<EmployeeDto> updateEmployeeFields(UUID id, EmployeeDto partialEmployee) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<EmployeeDto> deleteEmployeeById(UUID id) {
		StringBuilder queryString = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		Optional<EmployeeDto> employee = getEmployeeByID(id);
		
		queryString.append("DELETE FROM employees_table WHERE business_entityid = :id;");
		params.addValue("id", id);
		
		template.update(queryString.toString(), params);
		return employee;
	}

	// Helper to check if an employee exists by id
	private boolean existsById(UUID id) {
		StringBuilder queryBuilder = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		queryBuilder.append(String.format("SELECT COUNT(*) FROM %s ", TABLE_NAME));
		queryBuilder.append("WHERE business_entityid = :id;");
		
		params.addValue("id", id);
		
		Long result = template.queryForObject(queryBuilder.toString(), params, Long.class);
		
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}
	// Helper function to get the size of the named table
	private Long getTableSize() {
		StringBuilder query = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		query.append("SELECT COUNT(*) FROM " + TABLE_NAME + ";");
		
		return Long.valueOf(
				template.queryForObject(query.toString(), params, Long.class));
	}
	
	// Helper function to set up the UPDATE query statement with all the db columns and DTO attributes
	private void writeUpdateQuery(StringBuilder query) {
		// Table
		query.append(String.format("UPDATE %s ", TABLE_NAME));
		
		// Columns
		query.append("SET ");
		
		query.append("nationalidnumber = :nationalIDNumber, ");
		query.append("loginid = :loginID , ");
		query.append("organization_node = :organizationNode, ");
		query.append("organization_level = :organizationLevel, ");
		query.append("job_title = :jobTitle, ");
		
		query.append("birth_date = :birthDate, ");
		query.append("marital_status = :maritalStatus, ");
		query.append("gender = :gender, ");
		query.append("hire_date = :hireDate, ");
		query.append("salaried_flag = :salariedFlag, ");
		
		query.append("vacation_hours = :vacationHours, ");
		query.append("sick_leave_hours = :sickLeaveHours, ");
		query.append("current_flag = :currentFlag, ");
		query.append("row_guid = :rowGuid, ");
		
		// Modified
		query.append("modified_date = :modifiedDate ");
		
		// Restrictions
		query.append("WHERE business_entityid = :businessEntityID ;");
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
		param.addValue("organizationNode", dto.getOrganizationNode());
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
	
	// Helper function to quickly load up update names as parameter names and change update time
	private void loadUpdateParams(MapSqlParameterSource param, EmployeeDto dto) {
		loadDtoParams(param, dto);
		param.addValue("modifiedDate", LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS"))
				.toString());
	
	}
}
