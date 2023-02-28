package com.segurosbolivar.refactoring.errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.segurosbolivar.refactoring.errors.GlobalExceptionHandler.ErrorPayload;

class BackendLibraryApplicationTests {
	
	RestTemplate restTemplate = new RestTemplate();
	MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void contextLoads() throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String applicationName = "Test By Now";

		ErrorPayload errorPayload = new ErrorPayload();
		errorPayload.setException(new Exception());
		errorPayload.setApplicationName(applicationName);


		Long expectedResponse = 123L;

		mockServer.expect(requestTo("http://localhost:8080/aplicacionBackendError/save"))
		          .andExpect(method(HttpMethod.POST))
		          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		          .andRespond(withSuccess(objectMapper.writeValueAsString(expectedResponse), MediaType.APPLICATION_JSON));
		
		Long response = GlobalExceptionHandler.catchException(new Exception());
		mockServer.verify();

		assertEquals(expectedResponse, response);

	}
}
