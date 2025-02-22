package com.crud.all.funcionarios.exceptions;

public class FuncionarioNotFoundException extends RuntimeException{
    public FuncionarioNotFoundException(String message) {
        super(message);
    }
}
