package com.andersenlab.exceptions;

public class InappropriateValueException extends RuntimeException{
    public InappropriateValueException(String message) {
        super(message);
    }
}
