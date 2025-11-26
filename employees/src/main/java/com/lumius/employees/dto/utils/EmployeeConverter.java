package com.lumius.employees.dto.utils;

import com.lumius.employees.dto.EmployeeDTO;
import com.lumius.employees.entities.EmployeeEntity;

/**
 * @author Razvan
 * Utility class that converts between Employee DTOs and Entities
 * Used by JPA service implementation
 */
public class EmployeeConverter {

	public EmployeeDTO toDTO(EmployeeEntity entity) {
		return EmployeeDTO.builder()
				.businessEntityID(entity.getBusinessEntityID())
				.nationalIDNumber(entity.getNationalIDNumber())
				.loginID(entity.getLoginID())
				.organizationNode(entity.getOrganizationNode())
				.organizationLevel(entity.getOrganizationLevel())
				.jobTitle(entity.getJobTitle())
				.birthDate(entity.getBirthDate())
				.maritalStatus(entity.getMaritalStatus())
				.gender(entity.getGender())
				.hireDate(entity.getHireDate())
				.salariedFlag(entity.getSalariedFlag())
				.vacationHours(entity.getVacationHours())
				.sickLeaveHours(entity.getSickLeaveHours())
				.currentFlag(entity.getCurrentFlag())
				.rowGuid(entity.getRowGuid())
				.modifiedDate(entity.getModifiedDate())
				.build();
	}
	
	public EmployeeEntity toEntity(EmployeeDTO dto) {
		return EmployeeEntity.builder()
				.businessEntityID(dto.getBusinessEntityID())
				.nationalIDNumber(dto.getNationalIDNumber())
				.loginID(dto.getLoginID())
				.organizationNode(dto.getOrganizationNode())
				.organizationLevel(dto.getOrganizationLevel())
				.jobTitle(dto.getJobTitle())
				.birthDate(dto.getBirthDate())
				.maritalStatus(dto.getMaritalStatus())
				.gender(dto.getGender())
				.hireDate(dto.getHireDate())
				.salariedFlag(dto.getSalariedFlag())
				.vacationHours(dto.getVacationHours())
				.sickLeaveHours(dto.getSickLeaveHours())
				.currentFlag(dto.getCurrentFlag())
				.rowGuid(dto.getRowGuid())
				.modifiedDate(dto.getModifiedDate())
				.build();
	}
}
