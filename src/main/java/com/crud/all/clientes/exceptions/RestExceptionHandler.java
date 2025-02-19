package com.crud.all.clientes.exceptions;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Hidden
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    private ResponseEntity<Object> ClienteNotFoundHandler(ClienteNotFoundException exception) {
        return buildResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteJaExisteException.class)
    private ResponseEntity<Object> ClienteJaExisteHandler(ClienteJaExisteException exception) {
        return buildResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidacao(ValidacaoException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailInvalidoException.class)
    public ResponseEntity<Object> handleValidacaoEmail(EmailInvalidoException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
