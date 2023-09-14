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
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.swagger_spring_security.dto.CustomerDTO;
import com.testing.swagger_spring_security.service.CustomerService;
import com.testing.swagger_spring_security.util.Views;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Creating success."),
			@ApiResponse(code = 400, message = "Creating fail.") })
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public CustomerDTO createCustomer(
			@RequestBody @JsonView(value = Views.UserView.Request.class) CustomerDTO customerDto) {
		CustomerDTO result = custService.createCustomer(customerDto);
		return result;
	}
	
	@ApiOperation(value = "findCustomer", tags = "customer", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Valid Customer."),
			@ApiResponse(code = 400, message = "Invalid Customer.") })
	@RequestMapping(method = RequestMethod.POST, value = "/get/{id}")
	public CustomerDTO findCustomer(@PathVariable Long id) {
		CustomerDTO result = custService.getById(id);
		return result;
	}

}
