package com.segurosbolivar.refactoring.techcamp.errors.service.implementation;

import org.springframework.stereotype.Service;

import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.EmailSenderServiceI;

import jakarta.mail.MessagingException;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailSenderServiceImp implements EmailSenderServiceI {
	
	@Autowired
	private final JavaMailSender mailSender;
	
	public EmailSenderServiceImp(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	}


	@Override
	public void sendEmail(String to, Long id) throws MessagingException {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	    simpleMailMessage.setFrom("testapperrores@gmail.com");
	    simpleMailMessage.setTo(to);
	    simpleMailMessage.setSubject("Creación de ticket");
	    LocalDate date = LocalDate.now();
	    String messsage = "Se ha creado un ticket con los siguientes detalles:\n\n" +
                "ID del ticket: " + id + "\n" +
                "Fecha de creación: " + date.toString()+ "\n" +
                "Si necesita más información o tiene alguna pregunta, no dude en ponerse en contacto con nosotros.";
	    simpleMailMessage.setText(messsage);

	    this.mailSender.send(simpleMailMessage);
	}
	
	

}
