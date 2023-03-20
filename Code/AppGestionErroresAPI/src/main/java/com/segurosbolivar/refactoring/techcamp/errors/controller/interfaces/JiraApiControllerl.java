package com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces;

import com.segurosbolivar.refactoring.techcamp.errors.request.ApiJiraRequest;
import com.segurosbolivar.refactoring.techcamp.errors.request.ResponseJira;
import org.codehaus.jackson.JsonNode;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpResponse;

public interface JiraApiControllerl {



    ResponseEntity<ResponseJira> createIssue(ApiJiraRequest apiJiraRequest);
}
