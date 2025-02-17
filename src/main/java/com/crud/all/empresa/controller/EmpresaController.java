package com.crud.all.empresa.controller;

import com.crud.all.empresa.dto.EmpresaDTO;
import com.crud.all.empresa.dto.ResponseDTO;
import com.crud.all.empresa.entity.Empresa;
import com.crud.all.empresa.service.EmpresaService;
import com.crud.all.infra.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/empresas")
@Tag(name = "Empresa", description = "Controller de empresa")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

    @Operation(summary = "Criar empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisção inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping()
    public ResponseEntity create(@RequestBody Empresa empresa) {
        try {
            ResponseDTO responseDTO = this.empresaService.create(empresa);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping()
    public ResponseEntity<List<EmpresaDTO>> getAll() {
        try {
            List<EmpresaDTO> empresaDTOS = this.empresaService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(empresaDTOS);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Buscar empresa por UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa buscada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisção inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<EmpresaDTO> get(@PathVariable UUID uuid) {
        try {
            EmpresaDTO empresaDTO = this.empresaService.getByUuid(uuid);
            return ResponseEntity.status(HttpStatus.OK).body(empresaDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> delete(@PathVariable UUID uuid) {
        try {
            String message = this.empresaService.delete(uuid);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Editar empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa editada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisção inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<EmpresaDTO> editar(@PathVariable UUID uuid, @RequestBody Empresa empresa) {
        try {
            EmpresaDTO empresaDTO = this.empresaService.editar(uuid, empresa);
            return ResponseEntity.status(HttpStatus.OK).body(empresaDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
