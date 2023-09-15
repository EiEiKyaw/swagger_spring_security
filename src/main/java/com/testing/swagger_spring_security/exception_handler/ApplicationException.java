package com.testing.swagger_spring_security.exception_handler;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter		
@AllArgsConstructor
public class ApplicationException extends RuntimeException {
    
	private static final long serialVersionUID = -8664225378531483486L;
	
	private final String errorCode;
    private final String message;
    private final List<String> errors;
    private final HttpStatus httpStatus;
}
