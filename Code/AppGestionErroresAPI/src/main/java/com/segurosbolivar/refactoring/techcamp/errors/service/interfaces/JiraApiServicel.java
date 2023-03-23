package com.segurosbolivar.refactoring.techcamp.errors.service.interfaces;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.segurosbolivar.refactoring.techcamp.errors.request.ResponseJira;

public interface JiraApiServicel {

    public void getIssue(String issueKey, String email, String apiToken) throws Exception ;

    public ResponseJira createIssue(String summary, String description, String projectName, String parent,String idError) throws Exception ;

    }