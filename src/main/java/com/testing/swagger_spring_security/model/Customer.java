package com.testing.swagger_spring_security.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name is mandatory")
	@Column(nullable = false)
	private String name;

	private String address;

	@NotBlank(message = "Phone is mandatory")
	@Column(nullable = false)
	private String phone;

	private String email;

	private Integer weight;

	@NotNull(message = "Quantity is mandatory")
	@Column(name = "qty")
	private Integer quantity;

	@NotNull(message = "Pickup Date is mandatory")
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date pickupDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	@PrePersist
	private void setCreatedDate() {
		this.createdDate = new Date();
	}

}
