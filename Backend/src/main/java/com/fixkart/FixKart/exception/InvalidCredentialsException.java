package com.fixkart.FixKart.exception;

// If Password not matched
public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
