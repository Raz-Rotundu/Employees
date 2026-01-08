package com.lumius.employees.dto.descriptors;

import org.springframework.hateoas.RepresentationModel;

import com.lumius.employees.dto.EmployeeDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeDescriptor extends RepresentationModel<EmployeeDescriptor>{

	private EmployeeDto employeeDto;
}
