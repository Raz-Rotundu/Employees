package com.lumius.employees.controller.utils;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.lumius.employees.controller.EmployeeControllerV2;
import com.lumius.employees.dto.EmployeeDto;
import com.lumius.employees.dto.descriptors.EmployeeCollectionDescriptor;
import com.lumius.employees.dto.descriptors.EmployeeDescriptor;

@Component
public class EmployeeHyperMediaUtils {
	
	public EmployeeDescriptor describeEmployeeDto(EmployeeDto dto) {
		return Stream.of(new EmployeeDescriptor() )
				.peek(descriptor -> 
					descriptor.setEmployeeDto(dto) )
				.peek(descriptor -> descriptor.add(
								WebMvcLinkBuilder.linkTo(
										EmployeeControllerV2.class)
				.slash(dto.getBusinessEntityID() ).withSelfRel() ))
				.findFirst().get() ;
	}
	
	public EmployeeCollectionDescriptor describeEmployeeCollection(List<EmployeeDto> dtoList) {
		
		if(dtoList.isEmpty()) return null;
		
		List<EmployeeDescriptor> parsedEmployees = parseEmployees(dtoList);
		
		return Stream.of(new EmployeeCollectionDescriptor())
				.peek(cDes -> 
					cDes.setEmployeeCollectionDescriptor(parsedEmployees))
				.peek(this::addCollectionLink)
				.findFirst().get();
		
		
	}
	
	//Parsing helper
	private List<EmployeeDescriptor> parseEmployees(List<EmployeeDto> employeesList) {
		
		return employeesList.stream()
		.map(this::describeEmployeeDto)
		.toList();
	}
	
	//Helper to add collection links
	public void addCollectionLink(EmployeeCollectionDescriptor dtoCollection) {
		dtoCollection.add(
				WebMvcLinkBuilder.linkTo(EmployeeControllerV2.class)
				.withRel("allProperties"));
	}

}
