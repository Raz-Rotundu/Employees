package com.lumius.employees.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Razvan
 * EmployeeDTO represents an employee in the Employees table
 * All fields are required except for organizationNode and organizationLevel
 * LocalDate and LocalDateTime fields are specifically parsed according to pattern
 */
@Builder
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor @AllArgsConstructor
public class EmployeeDto {

	/**
	 * The ID of the employee within the company, uniquely generated
	 */
	@NotNull(message = "Business Entity ID cannot be null")
	UUID businessEntityID;
	
	/**
	 * The Country ID of the employee representing what country they work in
	 */
	@NotEmpty(message = "National ID cannot be empty")
	String nationalIDNumber;
	
	/**
	 * The login ID/username for the user in the company portal
	 */
	@NotEmpty(message = "loginId cannot be empty")
	String loginID;
	
	// These two can be empty
	String organizationNode;
	String organizationLevel;
	
	/**
	 * Employee job title
	 */
	@NotEmpty(message = "Job title cannot be empty")
	String jobTitle;
	
	/**
	 * Employee birth date, formatted to yyyy-MM-dd
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Birthdate cannot be null")
	LocalDate birthDate;
	
	/**
	 * Employee marital status
	 */
	@NotEmpty(message = "Marital status cannot be empty")
	String maritalStatus;
	
	/**
	 * Employee gender
	 */
	@NotEmpty(message = "Gender cannot be empty")
	String gender;
	
	/**
	 * Date on which the employee was hired
	 * Formatted to yyyy-MM-dd
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Hiring date Cannot be empty")
	LocalDate hireDate;
	
	/**
	 * Indicates if the employee is salaried or not (T or F)
	 */
	@NotEmpty(message = "Salaried flag cannot be empty")
	String salariedFlag;
	
	/**
	 * Number of vacation hours the employee is entitled to
	 */
	@NotNull(message = "Vacation hours cannot be empty")
	Integer vacationHours;
	
	/**
	 * Number of sick leave hours an employee is entitled to 
	 */
	@NotNull(message = "Sick leave hours cannot be empty")
	Integer sickLeaveHours;
	
	/**
	 * Indicates if this person is currently employed at the company (T or F)
	 */
	@NotEmpty(message = "Current flag cannot be empty")
	String currentFlag;
	

	@NotNull(message = "Row GUID cannot be empty")
	UUID rowGuid;
	
	/**
	 * Last time this employee record was modified
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSS")
	@NotNull(message = "Modified date cannot be empty")
	LocalDateTime modifiedDate;
	
	
	
}
