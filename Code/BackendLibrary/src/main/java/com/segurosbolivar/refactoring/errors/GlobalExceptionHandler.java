package com.segurosbolivar.refactoring.errors;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.segurosbolivar.refactoring.errors.service.AplicacionErrorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * GlobalExceptionHandler is a class that provides a common exception handling mechanism for the entire application.
 * It extends the ResponseEntityExceptionHandler class and overrides the handleConflict method to handle all the exceptions of type Exception.
 * The handleConflict method returns a response entity that contains a custom exception message and the HTTP status code for the conflict.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private AplicacionErrorService aplicacionErrorService;
	/**
     * Handles all uncaught exception persisting in the database without generating a report yet.
     * 
     * @param ex The exception to be handled.
     * @param request The web request associated with the exception.
     * @return A response entity that contains a custom exception message and the HTTP status code for the conflict.
     */
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
    	

    	Map<String,Long> responseMap = new HashMap<>();
    	
    	Long errorId = catchException(ex);
    	responseMap.put("id_application_error", errorId);
    	String bodyOfResponse = "";
        try {
        	ObjectMapper mapper = new ObjectMapper();
        	bodyOfResponse = mapper.writeValueAsString(responseMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    
    /**
     * Generate a custom report of any exception received.
     * 
     * @param ex The exception to be reported.
     */
    public Long catchException(Exception ex) {
    	
    	System.out.println("==============================");
    	System.out.println("Exception catched in library");
    	System.out.println("==============================");
    	
		return aplicacionErrorService.persistAplicacionError(ex);
    }
}
