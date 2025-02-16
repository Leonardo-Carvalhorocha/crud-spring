package com.crud.all.clientes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    private ResponseEntity<String> ClienteNotFoundHandler(ClienteNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não existe");
    }
}
