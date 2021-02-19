package com.myclass.exceptions;

public class ProjectIdExceptionReponse {

    private String projectIdentifier;
    public ProjectIdExceptionReponse(String projectIdentifier){
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
