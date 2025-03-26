package com.crud.all.api.servicos.controller;

import com.crud.all.api.clientes.dto.ClienteDTO;
import com.crud.all.api.servicos.dto.ServicoDTO;
import com.crud.all.api.servicos.entity.Servico;
import com.crud.all.api.servicos.service.ServicoService;
import com.crud.all.infra.security.SecurityConfig;
import com.crud.all.response.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("api/servicos")
@Tag(name = "Servico", description = "Controller de servicos")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class ServicoController {

    @Autowired
    ServicoService servicoService;

    @Operation(summary = "Criar Servico")
    @PostMapping()
    public ResponseEntity<GenericResponse> create(@RequestBody Servico servico) {
        ServicoDTO servicoDTO = this.servicoService.create(servico);
        GenericResponse<ServicoDTO, Object> response = new GenericResponse<>("Servico editado com sucesso", servicoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Buscar todos Servico")
    @GetMapping("/{uuidEmpresa}")
    public ResponseEntity<Page<ServicoDTO>> getAll(@PathVariable UUID uuidEmpresa, Pageable pageable) {
        Page<ServicoDTO> page = this.servicoService.filter(uuidEmpresa, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Operation(summary = "Editar servico")
    @PutMapping("/{uuid}")
    public ResponseEntity<GenericResponse> editar(@PathVariable UUID uuid, @RequestBody Servico servico) {
        ServicoDTO servicoDTO = this.servicoService.editar(uuid, servico);
        GenericResponse<ServicoDTO, Object> response = new GenericResponse<>("Servico editado com sucesso", servicoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Deletar servico")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<GenericResponse> delete(@PathVariable UUID uuid) {
        String message = this.servicoService.delete(uuid);
        GenericResponse<ServicoDTO, Object> response = new GenericResponse<>(message, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
