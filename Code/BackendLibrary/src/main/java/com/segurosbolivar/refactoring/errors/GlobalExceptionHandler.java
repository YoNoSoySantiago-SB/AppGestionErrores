package com.segurosbolivar.refactoring.errors;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * GlobalExceptionHandler is a class that provides a common exception handling mechanism for the entire application.
 * It extends the ResponseEntityExceptionHandler class and overrides the handleConflict method to handle all the exceptions of type Exception.
 * The handleConflict method returns a response entity that contains a custom exception message and the HTTP status code for the conflict.
 */
@Component
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public GlobalExceptionHandler() {
		 
	}
   
	/**
     * Handles all uncaught exception persisting in the database without generating a report yet.
     * 
     * @param ex The exception to be handled.
     * @param catchRequest The web request associated with the exception.
     * @return A response entity that contains a custom exception message and the HTTP status code for the conflict.
     */
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest catchRequest) {
    	
    	Map<String,Long> responseMap = new HashMap<>();
    	
    	Long errorId = reportError(ex);
    	responseMap.put("id_application_error", errorId);
    	String bodyOfResponse = "";
        try {
        	ObjectMapper mapper = new ObjectMapper();
        	bodyOfResponse = mapper.writeValueAsString(responseMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, catchRequest);
    }
    
    /**
     * Generate a custom report of any exception received.
     * 
     * @param ex The exception to be reported.
     * @return  A response entity that contains a custom exception message and the HTTP status code for the conflict.
     */
    public ResponseEntity<Object> catchException(Exception ex) {
    	Long errorId = reportError(ex);
       
        Map<String,Long> responseMap = new HashMap<>();
        responseMap.put("id_application_error", errorId);
        
        String bodyOfResponse = "";
        try {
        	ObjectMapper mapper = new ObjectMapper();
        	bodyOfResponse = mapper.writeValueAsString(responseMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
    	return new ResponseEntity<>(bodyOfResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Generate a custom report of any exception received.
     * 
     * @param ex The exception to be reported.
     * @return  the error id of the generated report
     */
    private Long reportError(Exception ex) {
    	
    	String url = env.getProperty("spring.api.base.url");
    	url = url+"/aplicacionBackendError/save";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        String applicationName = "Desconocido";
        ErrorPayload errorPayload = new ErrorPayload();
        errorPayload.setException(ex);
        errorPayload.setApplicationName(applicationName);

        HttpEntity<ErrorPayload> request = new HttpEntity<>(errorPayload, headers);

        Long errorId = restTemplate.postForObject(url, request, Long.class);
        
		return errorId;
    }
    
    public static class ErrorPayload {
        private Exception exception;
        private String applicationName;
        
        public Exception getException() {
        	return exception;
        }
        
        public String getApplicationName() {
        	return applicationName;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }
    }
}
