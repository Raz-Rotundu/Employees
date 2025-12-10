package com.lumius.employees.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees_table")
@Builder
@Data
@AllArgsConstructor @NoArgsConstructor
public class EmployeeEntity {
	
	/**
	 * The ID of the employee within the company, uniquely generated
	 */
	@Id
	@Column(nullable = false, updatable = false)
	@NotNull
	UUID businessEntityID;
	
	/**
	 * The Country ID of the employee representing what country they work in
	 */
	@Column(nullable = false)
	@NotEmpty
	String nationalIDNumber;
	
	/**
	 * The login ID/username for the user in the company portal
	 */
	@Column(nullable = false)
	@NotEmpty
	String loginID;
	
	// These two can be empty
	@Column(nullable = true)
	String organizationNode;
	@Column(nullable = true)
	String organizationLevel;
	
	/**
	 * Employee job title
	 */
	@Column(nullable = false)
	@NotEmpty
	String jobTitle;
	
	/**
	 * Employee birth date, formatted to yyyy-MM-dd
	 */
	@Column(nullable = false)
	@NotNull
	LocalDate birthDate;
	
	/**
	 * Employee marital status
	 */
	@Column(nullable = false)
	@NotEmpty
	String maritalStatus;
	
	/**
	 * Employee gender
	 */
	@Column(nullable = false)
	@NotEmpty
	String gender;
	
	/**
	 * Date on which the employee was hired
	 * Formatted to yyyy-MM-dd
	 */
	@Column(nullable = false)
	@NotNull
	LocalDate hireDate;
	
	/**
	 * Indicates if the employee is salaried or not (T or F)
	 */
	@Column(nullable = false)
	@NotEmpty
	String salariedFlag;
	
	/**
	 * Number of vacation hours the employee is entitled to
	 */
	@Column(nullable = false)
	@NotNull
	Integer vacationHours;
	
	/**
	 * Number of sick leave hours an employee is entitled to 
	 */
	@Column(nullable = false)
	@NotNull
	Integer sickLeaveHours;
	
	/**
	 * Indicates if this person is currently employed at the company (T or F)
	 */
	@Column(nullable = false)
	@NotEmpty
	String currentFlag;
	

	@Column(nullable = false)
	@NotNull
	UUID rowGuid;
	
	/**
	 * Last time this employee record was modified
	 */
	@Column(nullable = false)
	@NotNull
	LocalDateTime modifiedDate;
	
	@PrePersist
	protected void onCreate() {
		if (this.businessEntityID == null) {
			this.businessEntityID = UUID.randomUUID();
		}
		
	}
}
