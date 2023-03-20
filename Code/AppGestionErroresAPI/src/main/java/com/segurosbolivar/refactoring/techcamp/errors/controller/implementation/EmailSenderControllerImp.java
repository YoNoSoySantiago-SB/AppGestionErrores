package com.segurosbolivar.refactoring.techcamp.errors.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces.EmailSenderControllerI;
import com.segurosbolivar.refactoring.techcamp.errors.model.EmailMessage;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.EmailSenderServiceI;

import jakarta.mail.MessagingException;

@RestController
@CrossOrigin("*")
public class EmailSenderControllerImp implements EmailSenderControllerI{
	
	@Autowired
	private  EmailSenderServiceI emailSenderService;
	
	@PostMapping("/sendEmail")
    public ResponseEntity<String>  sendEmail(@RequestBody EmailMessage emailMessage) {
		try {
	        this.emailSenderService.sendEmail(emailMessage.getTo(), emailMessage.getIdError());
		} catch (MessagingException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Email Send", HttpStatus.CREATED);
    }

}
