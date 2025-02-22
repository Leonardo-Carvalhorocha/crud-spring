package com.crud.all.empresa.exceptions;

public class EmpresaNotFoundException extends RuntimeException{
    public EmpresaNotFoundException(String message) {
        super(message);
    }
}
