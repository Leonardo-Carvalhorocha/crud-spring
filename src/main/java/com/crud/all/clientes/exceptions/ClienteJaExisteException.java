package com.crud.all.clientes.exceptions;

public class ClienteJaExisteException extends RuntimeException {
    public ClienteJaExisteException(String message) {
        super(message);
    }
}
