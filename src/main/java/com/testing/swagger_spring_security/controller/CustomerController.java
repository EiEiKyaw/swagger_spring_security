package com.testing.swagger_spring_security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public List<CustomerDTO> getCustomerList() {
		return custService.getAll();
	}

	@ApiOperation(value = "createCustomer", tags = "customer", response = Iterable.class)
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public CustomerDTO createCustomer(
			@RequestBody @JsonView(value = Views.UserView.Request.class) CustomerDTO customerDto) {
		CustomerDTO result = custService.createCustomer(customerDto);
		return result;
	}

	@ApiOperation(value = "findCustomer", tags = "customer", response = Iterable.class)
	@RequestMapping(method = RequestMethod.POST, value = "/get/{id}")
	public CustomerDTO findCustomer(@PathVariable Long id) {
		CustomerDTO result = custService.getById(id);
		return result;
	}

	@ApiOperation(value = "updateCustomer", tags = "customer", response = Iterable.class)
	@RequestMapping(method = RequestMethod.POST, value = "/update/{id}")
	public CustomerDTO updateCustomer(
			@RequestBody @JsonView(value = Views.UserView.Request.class) CustomerDTO customerDto,
			@PathVariable(required = true) Long id) {
		customerDto.setId(id);
		CustomerDTO result = custService.updateCustomer(customerDto);
		return result;
	}

	@ApiOperation(value = "deleteCustomer", tags = "customer", response = Iterable.class)
	@RequestMapping(method = RequestMethod.POST, value = "/delete/{id}")
	public void deleteCustomer(@PathVariable(required = true) Long id) {
		custService.deleteCustomer(id);
	}

}
