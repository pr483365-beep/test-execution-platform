package com.ramya.shop.projectservice.exception;


public class ProjectNotFoundException extends RuntimeException{

    public ProjectNotFoundException(String message) {
        super(message);
    }

}
