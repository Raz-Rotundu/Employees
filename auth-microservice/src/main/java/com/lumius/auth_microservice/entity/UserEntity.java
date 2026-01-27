package com.lumius.auth_microservice.entity;

import java.util.UUID;

import com.lumius.auth_microservice.enumerable.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Razvan
 * Entity representing a user in the database
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
	
	@Id
	UUID id;
	
	@Column(unique = true, nullable = false)
	String email;
	
	String password;
	
	@Enumerated(EnumType.STRING)
	UserType userType;
	
	/**
	 * Auto assign an ID if not present
	 */
	@PrePersist
	private void onCreate() {
		if (id == null) {
			id = UUID.randomUUID();
		}
	}
}
