package com.andersenlab.exceptions;

public class ActionNotAllowedException extends RuntimeException{
    public ActionNotAllowedException(String message) {
        super(message);
    }
}
