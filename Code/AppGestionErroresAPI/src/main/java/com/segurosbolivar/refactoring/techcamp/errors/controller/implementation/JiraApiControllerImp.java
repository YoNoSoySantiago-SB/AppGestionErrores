package com.segurosbolivar.refactoring.techcamp.errors.controller.implementation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces.JiraApiControllerl;
import com.segurosbolivar.refactoring.techcamp.errors.request.ApiJiraRequest;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.JiraApiServicel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class JiraApiControllerImp implements JiraApiControllerl {

    @Autowired
    JiraApiServicel jiraApiServicel;


    @Override
    @PostMapping("/createIssue/save")
    public ResponseEntity<String> createIssue(@RequestBody ApiJiraRequest apiJiraRequest) {
        String resp;


        try{
            resp= jiraApiServicel.createIssue(apiJiraRequest.getSummary(),apiJiraRequest.getDescription(),apiJiraRequest.getProjectname());
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
        return  new ResponseEntity<>(resp, HttpStatus.CREATED);

    }
}
