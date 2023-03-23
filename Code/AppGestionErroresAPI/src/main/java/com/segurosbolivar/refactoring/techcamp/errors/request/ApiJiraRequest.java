package com.segurosbolivar.refactoring.techcamp.errors.request;


import lombok.Data;

@Data
public class ApiJiraRequest {

    private String summary;
    private String description;
    private String projectname;
    private String parent;
    private String idError;
}
