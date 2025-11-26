package com.lumius.employees.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lumius.employees.entities.EmployeeEntity;

/**
 @author Razvan
 JPA repository implementation using EmployeeEnitity as type, with UUID as the ID
 */
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {

}
