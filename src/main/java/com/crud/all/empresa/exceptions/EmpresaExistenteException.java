package com.crud.all.empresa.exceptions;

import org.springframework.stereotype.Component;

public class EmpresaExistenteException extends RuntimeException{
    public EmpresaExistenteException(String message) {
        super(message);
    }
}
