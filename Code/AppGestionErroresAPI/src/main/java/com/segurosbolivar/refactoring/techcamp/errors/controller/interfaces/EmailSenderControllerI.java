package com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces;

import org.springframework.http.ResponseEntity;
import com.segurosbolivar.refactoring.techcamp.errors.model.EmailMessage;

public interface EmailSenderControllerI {
	
	 public ResponseEntity<String> sendEmail(EmailMessage emailMessage) ;

}
