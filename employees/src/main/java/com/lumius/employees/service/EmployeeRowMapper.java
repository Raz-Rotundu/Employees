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
				.businessEntityID(UUID.fromString(rs.getString("businessEntityID")))
				
				.nationalIDNumber(rs.getString("nationalIDNumber"))
				.loginID(rs.getString("loginID"))
				
				.organizationNode(rs.getString("organizationNode"))
				.organizationLevel(rs.getString("organizationLevel"))
				
				.jobTitle(rs.getString("jobTitle"))
				

				.birthDate(rs.getTimestamp("birthDate")
						.toLocalDateTime()
						.toLocalDate())
				
				.maritalStatus(rs.getString("maritalStatus"))
				
				.gender(rs.getString("gender"))
				

				.hireDate(rs.getTimestamp("hireDate")
						.toLocalDateTime()
						.toLocalDate())
				
				.salariedFlag(rs.getString("salariedFlag"))
				
				.vacationHours(rs.getInt("vacationHours"))
				.sickLeaveHours(rs.getInt("vacationHours"))
				
				.currentFlag(rs.getString("currentFlag"))
				.rowGuid(UUID.fromString(rs.getString("rowGuid")))
				

				.modifiedDate(rs.getTimestamp("modifiedDate")
						.toLocalDateTime())
				
				.build();
	}


	

}
