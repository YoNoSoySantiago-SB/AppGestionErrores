package com.segurosbolivar.refactoring.techcamp.errors.service.interfaces;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public interface JiraApiServicel {

    public void getIssue(String issueKey, String email, String apiToken) throws Exception ;

    public String createIssue(String summary, String description, String projectName) throws Exception ;

    }