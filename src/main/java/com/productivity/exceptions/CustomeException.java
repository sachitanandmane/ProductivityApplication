package com.productivity.exceptions;

public class CustomeException extends RuntimeException{
    private String msg;
    public CustomeException(String msg){
        super(msg);
    }
}
