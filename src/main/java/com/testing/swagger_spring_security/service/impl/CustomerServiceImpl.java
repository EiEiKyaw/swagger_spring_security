package com.testing.swagger_spring_security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testing.swagger_spring_security.dto.CustomerDTO;
import com.testing.swagger_spring_security.model.Customer;
import com.testing.swagger_spring_security.repository.CustomerRepository;
import com.testing.swagger_spring_security.service.CustomerService;
import com.testing.swagger_spring_security.util.CommonUtil;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository custRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CustomerDTO createCustomer(CustomerDTO customerDto) {
		Customer entity = modelMapper.map(customerDto, Customer.class);
		entity = custRepo.save(entity);
		return modelMapper.map(entity, CustomerDTO.class);
	}

	@Override
	public CustomerDTO getById(Long id) {
		Optional<Customer> entity = custRepo.findById(id);
		if (entity.isPresent()) {
			return modelMapper.map(entity.get(), CustomerDTO.class);
		}
		return null;
	}

	@Override
	public List<CustomerDTO> getAll() {
		List<Customer> entities = custRepo.findAll();
		List<CustomerDTO> result = new ArrayList<>();
		if (CommonUtil.validList(entities)) {
			result = entities.stream().map(entity -> modelMapper.map(entity, CustomerDTO.class))
					.collect(Collectors.toList());
		}
		return result;
	}

}
