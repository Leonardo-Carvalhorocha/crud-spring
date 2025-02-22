package com.crud.all.funcionarios.exceptions;

import com.crud.all.utils.BuildResponseEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Hidden
@ControllerAdvice
public class RestExceptionHandlerFuncionario extends ResponseEntityExceptionHandler {

    @Autowired
    BuildResponseEntity buildResponseEntity;



}
