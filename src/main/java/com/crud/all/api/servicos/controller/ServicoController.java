package com.crud.all.api.servicos.controller;

import com.crud.all.api.servicos.entity.Servico;
import com.crud.all.api.servicos.service.ServicoService;
import com.crud.all.infra.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/servicos")
@Tag(name = "Servico", description = "Controller de servicos")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class ServicoController {

    @Autowired
    ServicoService servicoService;

    @Operation(summary = "Criar Servico")
    @PostMapping()
    public ResponseEntity create(@RequestBody Servico servico) {
        try{
            this.servicoService.create(servico);
            return ResponseEntity.status(HttpStatus.CREATED).body("Servico criado");
        }catch (RuntimeException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }



}
