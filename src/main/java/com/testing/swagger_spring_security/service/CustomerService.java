package com.testing.swagger_spring_security.service;

import java.util.List;

import com.testing.swagger_spring_security.dto.CustomerDTO;

public interface CustomerService {
	
	CustomerDTO createCustomer(CustomerDTO customer);
	
	CustomerDTO getById(Long id);
	
	List<CustomerDTO> getAll();

	CustomerDTO updateCustomer(CustomerDTO customerDto);

	void deleteCustomer(Long id);

}
