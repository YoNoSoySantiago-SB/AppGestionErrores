package com.segurosbolivar.refactoring.errors;

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
	
	/**
     * Handles all the exceptions of type Exception.
     * 
     * @param ex The exception to be handled.
     * @param request The web request associated with the exception.
     * @return A response entity that contains a custom exception message and the HTTP status code for the conflict.
     */
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
    	System.out.println("==============================");
    	System.out.println("Exception catched in library");
    	System.out.println("==============================");
    	
        String bodyOfResponse = "This is an exception message.";
        
        catchException(ex);
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    
    /**
     * Generate a custom report of any exception received.
     * 
     * @param ex The exception to be reported.
     */
    public static void catchException(Exception ex) {
    	StackTraceElement[] elements = ex.getStackTrace();
    	System.out.println(elements[0].getFileName());
    	System.out.println("///////////// FIN \\\\\\\\\\\\");
    }
    
//    // TODO
//    private void persistException() {
//    	
//    }
//    
//    // TODO
//    private void somthingToProcessFile() {
//    	
//    }
}
