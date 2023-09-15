package com.testing.swagger_spring_security.exception_handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<?> handleApplicationException(final ApplicationException exception,
			final HttpServletRequest request) {
		return new ResponseEntity<>(generateNormalResponse(exception, request), exception.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUnknownException(final Exception exception, final HttpServletRequest request) {
		var guid = UUID.randomUUID().toString();
		log.error(String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()), exception);
		var response = new ApiErrorResponse(guid,
				HttpStatus.INTERNAL_SERVER_ERROR.value() + " - " + HttpStatus.INTERNAL_SERVER_ERROR.name(),
				new ArrayList<>(), request.getRequestURI(), request.getMethod(), new Date());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(final ApplicationException exception,
			final HttpServletRequest request) {
		return new ResponseEntity<>(generateNormalResponse(exception, request), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(final ApplicationException exception,
			final HttpServletRequest request) {
		return new ResponseEntity<>(generateNormalResponse(exception, request), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleIllegalStateException(final ApplicationException exception,
			final HttpServletRequest request) {
		return new ResponseEntity<>(generateNormalResponse(exception, request), HttpStatus.CONFLICT);
	}

	private Object generateNormalResponse(ApplicationException exception, HttpServletRequest request) {
		var guid = UUID.randomUUID().toString();
		log.error(String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()), exception);
		var response = new ApiErrorResponse(guid,
				exception.getHttpStatus().value() + " - " + exception.getHttpStatus().name(), exception.getErrors(),
				request.getRequestURI(), request.getMethod(), new Date());
		return response;
	}

}
