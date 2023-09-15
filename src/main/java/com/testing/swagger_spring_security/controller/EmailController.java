package com.testing.swagger_spring_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.testing.swagger_spring_security.dto.EmailDetail;
import com.testing.swagger_spring_security.service.EmailService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/mail")
@Tag(name = "mail")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@ApiOperation(value = "sendMail", tags = "mail")
	@RequestMapping(method = RequestMethod.POST, value = "/sendSimple")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String sendMail(@RequestBody EmailDetail detail) {
		String status = emailService.sendSimpleMail(detail);
		return status;
	}

	@ApiOperation(value = "sendMailWithAttachment", tags = "mail")
	@RequestMapping(method = RequestMethod.POST, value = "/sendWithAttachment")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String sendMailWithAttachment(@RequestBody EmailDetail detail) {
		String status = emailService.sendMailWithAttachment(detail);
		return status;
	}
	
}