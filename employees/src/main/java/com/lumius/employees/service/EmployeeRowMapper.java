package com.lumius.employees.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.lumius.employees.dto.EmployeeDto;

/**
 * @author Razvan
 * Utility class to convert a ResultSet from a Jdbc query into an EmployeeDto
 */
@Component
public class EmployeeRowMapper implements RowMapper<EmployeeDto> {

	@Override
	public EmployeeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return EmployeeDto.builder()
				.businessEntityID(UUID.fromString(rs.getString("business_entityid")))
				
				.nationalIDNumber(rs.getString("nationalidNumber"))
				.loginID(rs.getString("loginid"))
				
				.organizationNode(rs.getString("organization_node"))
				.organizationLevel(rs.getString("organization_level"))
				
				.jobTitle(rs.getString("job_title"))
				

				.birthDate(rs.getTimestamp("birth_date")
						.toLocalDateTime()
						.toLocalDate())
				
				.maritalStatus(rs.getString("marital_status"))
				
				.gender(rs.getString("gender"))
				

				.hireDate(rs.getTimestamp("hire_date")
						.toLocalDateTime()
						.toLocalDate())
				
				.salariedFlag(rs.getString("salaried_flag"))
				
				.vacationHours(rs.getInt("vacation_hours"))
				.sickLeaveHours(rs.getInt("vacation_hours"))
				
				.currentFlag(rs.getString("current_flag"))
				.rowGuid(UUID.fromString(rs.getString("row_guid")))
				

				.modifiedDate(rs.getTimestamp("modified_date")
						.toLocalDateTime())
				
				.build();
	}


	

}
