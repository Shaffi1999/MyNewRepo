package com.in28minutes.rest.webservices.restfulwebservices.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
	private LocalDateTime timestamp;
	private String message;
	private String details;
}