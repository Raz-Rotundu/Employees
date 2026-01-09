package com.lumius.employees.dto.descriptors;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeCollectionDescriptor extends RepresentationModel<EmployeeCollectionDescriptor>{

	private Page<EmployeeDescriptor> employeeCollectionDescriptor;
}
