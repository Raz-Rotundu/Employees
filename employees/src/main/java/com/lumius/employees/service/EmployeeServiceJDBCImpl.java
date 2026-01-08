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
		
		writeDtoQuery(query, TABLE_NAME);	
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

		if(existsById(id, TABLE_NAME)) {
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
				template.query(query.toString(), rowMapper), page, () -> getTableSize(TABLE_NAME));
	}

	@Override
	public Optional<EmployeeDto> updateEmployee(UUID id, EmployeeDto updatedEmployee) {
		StringBuilder queryBuilder = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		if (existsById(id, TABLE_NAME)) {
			
			writeUpdateQuery(queryBuilder, TABLE_NAME);
			
			
			loadUpdateParams(params, updatedEmployee);
			template.update(queryBuilder.toString(), params);
			
			return getEmployeeByID(id);
			
			
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<EmployeeDto> updateEmployeeFields(UUID id, EmployeeDto partialEmployee) {
		StringBuilder queryBuilder = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		if (existsById(id, TABLE_NAME)) {
			EmployeeDto originalEmployee = getEmployeeByID(id).get();
			
			// Build new DTO
			EmployeeDto updatedEmployee = updateDto(partialEmployee, originalEmployee);
			
			// Save new DTO
			writeUpdateQuery(queryBuilder, TABLE_NAME);
			
			//Load params
			loadUpdateParams(params, updatedEmployee);
			
			//Run query
			template.update(queryBuilder.toString(), params);
			
			return Optional.of(updatedEmployee);
			
			
			
		} else {
			return Optional.empty();
		}

	}

	@Override
	public Optional<EmployeeDto> deleteEmployeeById(UUID id) {
		StringBuilder queryString = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		Optional<EmployeeDto> employee = getEmployeeByID(id);
		
		queryString.append("DELETE FROM " + TABLE_NAME + " "
				+ "WHERE business_entityid = :id;");
		params.addValue("id", id);
		
		template.update(queryString.toString(), params);
		return employee;
	}
	
	

	/**
	 * Function to check if given id exists in the given table
	 * @param id the business_entityid value to search for
	 * @param tableName name of target table
	 * @return true if one or more rows with the given business_entityid value exist
	 */
	private boolean existsById(UUID id, String tableName) {
		StringBuilder queryBuilder = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		queryBuilder.append(String.format("SELECT COUNT(*) FROM %s ", tableName));
		queryBuilder.append("WHERE business_entityid = :id;");
		
		params.addValue("id", id);
		
		Long result = template.queryForObject(queryBuilder.toString(), params, Long.class);
		
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Function to get the size of the given table, assuming it exists
	 * @param tableName the name of target table
	 * @return
	 */
	private Long getTableSize(String tableName) {
		StringBuilder query = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		query.append("SELECT COUNT(*) FROM " + tableName + ";");
		
		return Long.valueOf(
				template.queryForObject(query.toString(), params, Long.class));
	}
	
	/**
	 * Append a string representing and UPDATE query for an EmployeeDto
	 * @param tableName the name of target table
	 * @param query the StringBuilder containing the query
	 */
	private void writeUpdateQuery(StringBuilder query, String tableName) {
		// Table
		query.append(String.format("UPDATE %s ", tableName));
		
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
	
	/**
	 * Load up a stringBuilder with a string representing an INSERT of the EmployeeDto, with named parameters
	 * @param tableName the name of the target table
	 * @param qStringBuilder representing the query
	 */
	private void writeDtoQuery(StringBuilder query, String tableName) {
		query.append("INSERT INTO " + tableName + " ");
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
	
	/**
	 * Function to set up namedParameterJdbcTemplate parameters to the given EmployeeDto values
	 * @param param MapSqlParameterSource containing parameters
	 * @param dto EmployeeDto from which to get parameter values
	 */
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
	
	/**
	 * Function to load up EmployeeDto parameter values based on given dto, but updates the modifiedDate parameter to current time
	 * @param param the MapSqlParameterSource object
	 * @param dto an EmployeeDto object
	 */
	private void loadUpdateParams(MapSqlParameterSource param, EmployeeDto dto) {
		loadDtoParams(param, dto);
		param.addValue("modifiedDate", LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS"))
				.toString());
	
	}
	
	
	/**
	 * Helper method to construct a new EmployeeDto, using the original as base
	 * only changing the values which are not null from newEmployee
	 * @param newEmployee partially complete EmployeeDto
	 * @param original base values of the EmployeeDto
	 * @return a new EmployeeDto, containing new's values where not null, using original's values everywhere else
	 */
	private EmployeeDto updateDto(EmployeeDto newEmployee, EmployeeDto original) {
		return EmployeeDto.builder()
		// Unchanged
		.businessEntityID(original.getBusinessEntityID())
		
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
		
		.build();
	}
	
	/**
	 * Helper method to compare two values, returning new if new is not null or empty
	 * @param <T> The type of variable to be compared
	 * @param newValue the new value to compare
	 * @param oldValue the old value to compare with
	 * @return newValue if now null or empty
	 */
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
