package com.andersenlab.command;

public class CommandProviderException extends RuntimeException{
    public CommandProviderException() {
        super();
    }

    public CommandProviderException(String message) {
        super(message);
    }

    public CommandProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandProviderException(Throwable cause) {
        super(cause);
    }
}
