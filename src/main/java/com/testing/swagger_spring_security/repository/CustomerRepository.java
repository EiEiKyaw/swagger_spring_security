package com.testing.swagger_spring_security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.testing.swagger_spring_security.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	List<Customer> findByName(String name);

	Customer findByPhone(String phone);

	@Query("SELECT c FROM Customer c WHERE c.id != id AND c.phone = phone")
	Customer checkDuplicatePhone(Long id, String phone);

}
