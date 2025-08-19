package com.fixkart.FixKart.exception;

// If Wrong argument passed like in mobile number character passed
public class InvalidArgumentException extends RuntimeException{
    public InvalidArgumentException(String message) {
        super(message);
    }
}
