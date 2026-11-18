package com.ramya.shop.projectservice.exception;

public class ProjectMemberNotFoundException extends RuntimeException{

    public ProjectMemberNotFoundException(String message){
        super(message);
    }
}
