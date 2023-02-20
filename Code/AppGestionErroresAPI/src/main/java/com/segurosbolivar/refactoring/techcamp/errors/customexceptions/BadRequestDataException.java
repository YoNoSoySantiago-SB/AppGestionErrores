package com.segurosbolivar.refactoring.techcamp.errors.customexceptions;

public class BadRequestDataException extends Exception{
	public BadRequestDataException(String message) {
		super(message);
	}
	public BadRequestDataException() {
		super("Los datos de entrada de la solicitud no son validos");
	}
}
