package com.lumius.employees.controller.utils;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.lumius.employees.controller.EmployeeControllerV2;
import com.lumius.employees.dto.EmployeeDto;
import com.lumius.employees.dto.descriptors.EmployeeCollectionDescriptor;
import com.lumius.employees.dto.descriptors.EmployeeDescriptor;
/**
 * @author Razvan
 * Utility functions for converting DTOs and collections of DTO to descriptors and collections of descriptors
 */

@Component
public class EmployeeHyperMediaUtils {
	
	/**
	 * Convert an EmployeeDto into an EmployeeDescriptor
	 * @param dto and EmployeeDto
	 * @return an EmployeeDescriptor with the dto content and a controller reference
	 */
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
	
	/**
	 * Converts page of EmployeeDtos to a EmployeeCollectionDescriptor
	 * @param dtoPage
	 * @return
	 */
	public EmployeeCollectionDescriptor describeEmployeePage(Page<EmployeeDto> dtoPage) {
		
		if (dtoPage.isEmpty() )return null;
		
		Page<EmployeeDescriptor> parsedPage = parseEmployees(dtoPage);
		
		return Stream.of(new EmployeeCollectionDescriptor())
				.peek(cDes -> 
						cDes.setEmployeeCollectionDescriptor(parsedPage))
				.peek(this::addCollectionLink)
				.findFirst().get();
				
		
	}
	

	/**
	 * Converts a list of EmployeeDtos to a list of EmployeeDescriptors
	 * @param employeesList a list of EmployeeDtos
	 * @return a list of EmployeeDescriptors
	 */
	private List<EmployeeDescriptor> parseEmployees(List<EmployeeDto> employeesList) {
		
		return employeesList.stream()
		.map(this::describeEmployeeDto)
		.toList();
	}
	

	/**
	 * Converts a page of EmployeeDtos to page of EmployeeDescriptors
	 * @param page a page of EmployeeDtos
	 * @return a page of EmployeeDescriptors
	 */
	
	private Page<EmployeeDescriptor> parseEmployees(Page<EmployeeDto> page){
		return page.map(this::describeEmployeeDto);
	}
	

	/**
	 * Helper function to add collection link to a CollectionDescriptor
	 * @param dtoCollection
	 */
	public void addCollectionLink(EmployeeCollectionDescriptor dtoCollection) {
		dtoCollection.add(
				WebMvcLinkBuilder.linkTo(EmployeeControllerV2.class)
				.withRel("allProperties"));
	}

}
