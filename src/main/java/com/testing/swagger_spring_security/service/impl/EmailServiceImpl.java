package com.testing.swagger_spring_security.service.impl;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.testing.swagger_spring_security.dto.EmailDetail;
import com.testing.swagger_spring_security.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public String sendSimpleMail(EmailDetail detail) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(detail.getRecipient());
			mailMessage.setText(detail.getMsgBody());
			mailMessage.setSubject(detail.getSubject());
			javaMailSender.send(mailMessage);
			return "Mail sent successfully...";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error while Sending Mail";
		}
	}

	public String sendMailWithAttachment(EmailDetail detail) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(detail.getRecipient());
			mimeMessageHelper.setText(detail.getMsgBody());
			mimeMessageHelper.setSubject(detail.getSubject());

			FileSystemResource file = new FileSystemResource(new File(detail.getAttachment()));
			mimeMessageHelper.addAttachment(file.getFilename(), file);

			javaMailSender.send(mimeMessage);
			return "Mail sent Successfully";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail!!!";
		}
	}

}