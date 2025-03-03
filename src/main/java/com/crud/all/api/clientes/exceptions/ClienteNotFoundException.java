package com.crud.all.api.clientes.exceptions;

public class ClienteNotFoundException extends RuntimeException{
    public ClienteNotFoundException() {super("Cliente não encotrado ou não existe.");}

    public ClienteNotFoundException(String message) {super(message);}
}
