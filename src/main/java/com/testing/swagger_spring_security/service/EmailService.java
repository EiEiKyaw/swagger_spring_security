package com.testing.swagger_spring_security.service;

import com.testing.swagger_spring_security.dto.EmailDetail;

public interface EmailService {

	String sendSimpleMail(EmailDetail detail);

	String sendMailWithAttachment(EmailDetail detail);

}