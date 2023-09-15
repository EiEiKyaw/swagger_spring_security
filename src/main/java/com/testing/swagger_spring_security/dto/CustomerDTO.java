package com.testing.swagger_spring_security.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.testing.swagger_spring_security.util.Views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	@JsonView(value = { Views.UserView.Response.class })
	private Long id;

	@JsonView(value = { Views.UserView.Request.class })
	@NotBlank
	@Size(min = 3, max = 20)
	private String name;

	@JsonView(value = { Views.UserView.Request.class })
	private String address;

	@JsonView(value = { Views.UserView.Request.class })
	private String phone;

	@JsonView(value = { Views.UserView.Request.class })
	private String email;
	
	@JsonView(value = { Views.UserView.Request.class })
	private Integer weight;
	
	@JsonView(value = { Views.UserView.Request.class })
	@JsonProperty("qty")
	private Integer quantity;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonView(value = { Views.UserView.Request.class })
	@JsonProperty("pickup_date")
	private Date pickupDate;

}
