package com.lumius.employees.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class EmployeeDTO {

	@NotNull(message = "Business Entity ID cannot be null")
	UUID businessEntityID;
	
	@NotEmpty(message = "National ID cannot be empty")
	String nationalIDNumber;
	
	@NotEmpty(message = "loginId cannot be empty")
	String loginID;
	
	// These two can be empty
	String organizationNode;
	String organizationLevel;
	
	@NotEmpty(message = "Job title cannot be empty")
	String jobTitle;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Birthdate cannot be null")
	LocalDate birthDate;
	
	@NotEmpty(message = "Marital status cannot be empty")
	String maritalStatus;
	
	@NotEmpty(message = "Gender cannot be empty")
	String gender;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Hiring date Cannot be empty")
	LocalDate hireDate;
	
	@NotEmpty(message = "Salaried flag cannot be empty")
	String salariedFlag;
	
	@NotNull(message = "Vacation hours cannot be empty")
	Integer vacationHours;
	
	@NotNull(message = "Sick leave hours cannot be empty")
	Integer sickLeaveHours;
	
	@NotEmpty(message = "Current flag cannot be empty")
	String currentFlag;
	
	@NotNull(message = "Row GUID cannot be empty")
	UUID rowGuid;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSS")
	@NotNull(message = "Modified date cannot be empty")
	LocalDateTime modifiedDate;
	
	
	
}
