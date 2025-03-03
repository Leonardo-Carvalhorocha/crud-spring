package com.crud.all.api.clientes.exceptions;

import com.crud.all.utils.BuildResponseEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Hidden
@ControllerAdvice
public class RestExceptionHandlerCliente extends ResponseEntityExceptionHandler {

//    private final BuildResponseEntity buildResponseEntity;
//
//    public RestExceptionHandlerCliente(BuildResponseEntity buildResponseEntity) {
//        this.buildResponseEntity = buildResponseEntity;
//    }
    @Autowired
    BuildResponseEntity buildResponseEntity;

    @ExceptionHandler(ClienteNotFoundException.class)
    private ResponseEntity<Object> ClienteNotFoundHandler(ClienteNotFoundException exception) {
        return this.buildResponseEntity.buildResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteJaExisteException.class)
    private ResponseEntity<Object> ClienteJaExisteHandler(ClienteJaExisteException exception) {
        return this.buildResponseEntity.buildResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidacao(ValidacaoException ex) {
        return this.buildResponseEntity.buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailInvalidoException.class)
    public ResponseEntity<Object> handleValidacaoEmail(EmailInvalidoException ex) {
        return this.buildResponseEntity.buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
