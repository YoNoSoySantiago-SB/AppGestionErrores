package com.segurosbolivar.refactoring.techcamp.errors.service.interfaces;

import jakarta.mail.MessagingException;

public interface EmailSenderServiceI {

	void sendEmail(String to, String id) throws MessagingException;

}
