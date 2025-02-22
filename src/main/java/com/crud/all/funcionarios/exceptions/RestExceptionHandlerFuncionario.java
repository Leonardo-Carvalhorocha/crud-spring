package com.crud.all.funcionarios.exceptions;

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
public class RestExceptionHandlerFuncionario extends ResponseEntityExceptionHandler {

    @Autowired
    BuildResponseEntity buildResponseEntity;

    @ExceptionHandler(FuncionarioNotFoundException.class)
    public ResponseEntity<Object> FuncionarioNotFoundHandler(FuncionarioNotFoundException exception) {
        return this.buildResponseEntity.buildResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailInvalidoFuncionarioException.class)
    public ResponseEntity<Object> EmailInvalidoFuncionarioHandler(EmailInvalidoFuncionarioException exception) {
        return this.buildResponseEntity.buildResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FuncionarioCampoObrigatorioException.class)
    public ResponseEntity<Object> FuncionarioCampoObrigatorioHandler(FuncionarioCampoObrigatorioException exception) {
        return this.buildResponseEntity.buildResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
