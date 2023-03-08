package com.segurosbolivar.refactoring.techcamp.errors;

import com.segurosbolivar.refactoring.techcamp.errors.service.implementation.JiraApiService;
import org.apache.http.HttpEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.HttpMethod;
import java.net.http.HttpHeaders;

@SpringBootApplication
public class AppGestionErroresApiApplication {
	public  static void main(String[] args) throws Exception {
		SpringApplication.run(AppGestionErroresApiApplication.class, args);


	}


	}



