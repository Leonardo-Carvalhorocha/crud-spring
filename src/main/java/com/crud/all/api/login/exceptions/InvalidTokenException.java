package com.crud.all.api.login.exceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
