package com.crud.all.api.funcionarios.exceptions;

public class FuncionarioNotFoundException extends RuntimeException{
    public FuncionarioNotFoundException(String message) {
        super(message);
    }
}
