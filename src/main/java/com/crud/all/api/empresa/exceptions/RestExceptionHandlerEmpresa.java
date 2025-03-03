package com.crud.all.api.empresa.exceptions;

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
public class RestExceptionHandlerEmpresa extends ResponseEntityExceptionHandler {

    @Autowired
    BuildResponseEntity buildResponseEntity;

    @ExceptionHandler(EmpresaExistenteException.class)
    private ResponseEntity<Object> CnpjExistenteHandler(EmpresaExistenteException exception) {
        return this.buildResponseEntity.buildResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailInvalidoEmpresaException.class)
    private ResponseEntity<Object> EmailInvalidoHandler(EmailInvalidoEmpresaException exception) {
        return this.buildResponseEntity.buildResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmpresaNotFoundException.class)
    private ResponseEntity<Object> empresaNotFoundHandler(EmpresaNotFoundException exception) {
        return this.buildResponseEntity.buildResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
