package com.testing.swagger_spring_security.exception_handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
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

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(final HttpServletRequest request) {
		var guid = UUID.randomUUID().toString();
		log.error(String.format("Error GUID=%s;", guid));
		List<String> errList = new ArrayList<>();
		errList.add("Username or password is incorrect.");
		ApiErrorResponse response = new ApiErrorResponse(guid,
				HttpStatus.UNAUTHORIZED.value() + " - " + HttpStatus.UNAUTHORIZED.name(), errList,
				request.getRequestURI(), request.getMethod(), new Date());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<?> handleExpiredJwtException(final HttpServletRequest request) {
		var guid = UUID.randomUUID().toString();
		log.error(String.format("Error GUID=%s;", guid));
		List<String> errList = new ArrayList<>();
		errList.add("Token is expired!");
		var response = new ApiErrorResponse(guid,
				HttpStatus.UNAUTHORIZED.value() + " - " + HttpStatus.UNAUTHORIZED.name(), errList,
				request.getRequestURI(), request.getMethod(), new Date());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(final HttpServletRequest request) {
		var guid = UUID.randomUUID().toString();
		log.error(String.format("Error GUID=%s;", guid));
		List<String> errList = new ArrayList<>();
		errList.add("Access denied!");
		var response = new ApiErrorResponse(guid, HttpStatus.FORBIDDEN.value() + " - " + HttpStatus.FORBIDDEN.name(),
				errList, request.getRequestURI(), request.getMethod(), new Date());
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
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
