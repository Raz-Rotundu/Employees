package com.lumius.employees.controller;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Razvan
 * A basic exception handler that returns detailed responses when 500 errors occur in application
 */

@RestControllerAdvice
public class EmployeeControllerAdvice {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ProblemDetail> handleRuntimeException(RuntimeException ex) {
		ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		pd.setTitle("An Internal Error has Occurred!");
		
		pd.setDetail("The following error has occurred: "
				+ ex.getMessage());
		
		pd.setInstance(URI.create("/api/v1/employees"));
		pd.setProperty("timestamp",
				LocalDateTime.now().toString());
		
		return new ResponseEntity<> (pd, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
