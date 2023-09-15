package com.testing.swagger_spring_security.exception_handler;

import java.util.Date;
import java.util.List;

import lombok.Data;
	
@Data
public class ApiErrorResponse {

	private final String guid;
    private final String status;
    private final List<String> error;
    private final String path;
    private final String method;
    private final Date timestamp;

}
