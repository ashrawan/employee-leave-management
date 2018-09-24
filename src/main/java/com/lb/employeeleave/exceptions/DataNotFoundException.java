package com.lb.employeeleave.exceptions;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message){
        super(message);
    }
}
