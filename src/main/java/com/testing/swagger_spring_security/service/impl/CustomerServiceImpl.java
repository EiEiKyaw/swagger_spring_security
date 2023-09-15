package com.testing.swagger_spring_security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.testing.swagger_spring_security.dto.CustomerDTO;
import com.testing.swagger_spring_security.exception_handler.ApplicationException;
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
		List<String> errList = validateCreateCustomer(customerDto);
		if (CommonUtil.validList(errList)) {
			throw new ApplicationException("CustomerService - error in createCustomer()",
					String.format("Unsuccess create customer"), errList, HttpStatus.BAD_REQUEST);
		}

		Customer entity = custRepo.save(modelMapper.map(customerDto, Customer.class));
		return modelMapper.map(entity, CustomerDTO.class);
	}

	@Override
	public CustomerDTO getById(Long id) {
		Optional<Customer> entity = custRepo.findById(id);
		if (!entity.isPresent()) {
			throw new ApplicationException("CustomerService - error in getById()", String.format("Customer with id=%d not found", id),
					new ArrayList<>(), HttpStatus.NOT_FOUND);
		}
		return modelMapper.map(entity.get(), CustomerDTO.class);
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

	@Override
	public CustomerDTO updateCustomer(CustomerDTO customerDto) {
		List<String> errList = validateCreateCustomer(customerDto);
		Optional<Customer> entity = custRepo.findById(customerDto.getId());
		if (!entity.isPresent()) {
			errList.add("Customer is not found.");
			throw new ApplicationException("CustomerService - error in updateCustomer()",
					String.format("Customer with id=%d not found", customerDto.getId()), errList, HttpStatus.NOT_FOUND);
		}
		if (CommonUtil.validList(errList)) {
			throw new ApplicationException("CustomerService - error in updateCustomer()",
					String.format("Required customer fields must be valid"), errList, HttpStatus.BAD_REQUEST);
		}
		Customer newEntity = custRepo.save(modelMapper.map(customerDto, Customer.class));
		return modelMapper.map(newEntity, CustomerDTO.class);
	}

	private List<String> validateCreateCustomer(CustomerDTO customerDto) {
		List<String> errList = new ArrayList<>();
		if (!CommonUtil.validString(customerDto.getName())) {
			errList.add("Please enter customer name.");
		}
		if (!CommonUtil.validString(customerDto.getPhone())) {
			errList.add("Please enter customer phone.");
		}
		if(!CommonUtil.validInteger(customerDto.getQuantity())) {
			errList.add("Please enter quantity.");
		}
		if(!CommonUtil.validGreaterCurrent(customerDto.getPickupDate())) {
			errList.add("Please enter pick-up date after today.");
		}
		return errList;
	}

	@Override
	public void deleteCustomer(Long id) {
		List<String> errList = new ArrayList<>();
		Optional<Customer> entity = custRepo.findById(id);
		if (!entity.isPresent()) {
			errList.add("Customer is not found.");
			throw new ApplicationException("CustomerService - error in deleteCustomer()",
					String.format("Customer with id=%d not found", id), errList, HttpStatus.NOT_FOUND);
		}
		custRepo.deleteById(id);
	}


}
