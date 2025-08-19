package com.fixkart.FixKart.exception;

// If user already exist with the mobile number
public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
