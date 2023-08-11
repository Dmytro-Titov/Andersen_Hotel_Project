package com.andersenlab.exceptions;

public class UnknownCommandException extends RuntimeException{
    public UnknownCommandException(String command) {
        super(command.isEmpty() ? "Command is empty." : "Command '%s' not recognized.".formatted(command));
    }
}
