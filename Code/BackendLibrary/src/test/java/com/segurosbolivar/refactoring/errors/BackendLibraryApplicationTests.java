package com.segurosbolivar.refactoring.errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

@TestInstance(Lifecycle.PER_CLASS)
class BackendLibraryApplicationTests {
	
	@Mock
    private Environment env;
	
	@Mock
	private RestTemplate template;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(env.getProperty("spring.api.base.url")).thenReturn("http://localhost:8080");
    }

    @Test
    public void testHandleConflict() {
        Exception ex = new Exception("Test Exception");
        WebRequest request = mock(WebRequest.class);
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleConflict(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void testCatchException() {
        Exception ex = new Exception("Test Exception");
        ResponseEntity<Object> responseEntity = globalExceptionHandler.catchException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
}
