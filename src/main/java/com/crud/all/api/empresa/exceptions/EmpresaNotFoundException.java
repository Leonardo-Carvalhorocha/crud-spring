package com.crud.all.api.empresa.exceptions;

public class EmpresaNotFoundException extends RuntimeException{
    public EmpresaNotFoundException(String message) {
        super(message);
    }
}
