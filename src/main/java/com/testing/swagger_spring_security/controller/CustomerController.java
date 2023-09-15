package com.testing.swagger_spring_security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.testing.swagger_spring_security.dto.CustomerDTO;
import com.testing.swagger_spring_security.service.CustomerService;
import com.testing.swagger_spring_security.util.Views;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/customer")
@Tag(name = "customer")
public class CustomerController {

	@Autowired
	private CustomerService custService;

	@ApiOperation(value = "getCustomerList", tags = "customer")
	@RequestMapping(method = RequestMethod.GET, value = "/all")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('NORMAL')")
	public List<CustomerDTO> getCustomerList() throws AccessDeniedException, ExpiredJwtException {
		return custService.getAll();
	}

	@ApiOperation(value = "createCustomer", tags = "customer")
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	@PreAuthorize("hasAuthority('MANAGER')")
	public CustomerDTO createCustomer(
			@RequestBody @JsonView(value = Views.UserView.Request.class) CustomerDTO customerDto)
			throws AccessDeniedException {
		CustomerDTO result = custService.createCustomer(customerDto);
		return result;
	}

	@ApiOperation(value = "findCustomer", tags = "customer", response = Iterable.class)
	@RequestMapping(method = RequestMethod.POST, value = "/get/{id}")
	@PreAuthorize("hasAuthority('MANAGER')")
	public CustomerDTO findCustomer(@PathVariable Long id) {
		CustomerDTO result = custService.getById(id);
		return result;
	}

	@ApiOperation(value = "updateCustomer", tags = "customer", response = Iterable.class)
	@RequestMapping(method = RequestMethod.POST, value = "/update/{id}")
	@PreAuthorize("hasAuthority('MANAGER')")
	public CustomerDTO updateCustomer(
			@RequestBody @JsonView(value = Views.UserView.Request.class) CustomerDTO customerDto,
			@PathVariable(required = true) Long id) {
		customerDto.setId(id);
		CustomerDTO result = custService.updateCustomer(customerDto);
		return result;
	}

	@ApiOperation(value = "deleteCustomer", tags = "customer", response = Iterable.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> deleteCustomer(@PathVariable(required = true) Long id) {
		custService.deleteCustomer(id);
		return new ResponseEntity<>("Deleted successfully!", HttpStatus.OK);
	}

}
