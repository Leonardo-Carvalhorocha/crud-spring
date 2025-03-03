package com.crud.all.api.funcionarios.controller;

import com.crud.all.api.funcionarios.dto.FuncionarioDTO;
import com.crud.all.api.funcionarios.dto.RequestFuncionario;
import com.crud.all.api.funcionarios.entity.Funcionario;
import com.crud.all.api.funcionarios.service.FuncionarioService;
import com.crud.all.infra.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/funcionarios")
@Tag(name = "Funcionario", description = "Controller para crud de Funcionario")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @Operation(summary = "Criar um funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisção inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping()
    public ResponseEntity<RequestFuncionario> create(@RequestBody Funcionario funcionario) {
        try {
            FuncionarioDTO funcionarioDTO = this.funcionarioService.create(funcionario);
            return ResponseEntity.status(HttpStatus.OK).body(new RequestFuncionario(funcionarioDTO, "Funcionario criado com sucesso"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Lista todos os funcionarios por empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "funcionários"),
            @ApiResponse(responseCode = "400", description = "Requisção inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{uuidEmpresa}")
    public ResponseEntity<List<FuncionarioDTO>> filter(@PathVariable UUID uuidEmpresa, Pageable pageable) {
        try {
            List<FuncionarioDTO> funcionarioDTOS = this.funcionarioService.filter(uuidEmpresa, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(funcionarioDTOS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Editar funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário editado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisção inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{uuidFuncionario}")
    public ResponseEntity<RequestFuncionario> editar(@PathVariable UUID uuidFuncionario, @RequestBody Funcionario funcionario) {
        try {
            FuncionarioDTO funcionarioDTO = this.funcionarioService.editar(uuidFuncionario, funcionario);
            return ResponseEntity.status(HttpStatus.OK).body(new RequestFuncionario(funcionarioDTO, "Funcionario editado com sucesso"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Deletar funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisção inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{uuidFuncionario}")
    public ResponseEntity<RequestFuncionario> deletar(@PathVariable UUID uuidFuncionario) {
        try {
            String message = this.funcionarioService.delete(uuidFuncionario);
            return ResponseEntity.status(HttpStatus.OK).body(new RequestFuncionario(null, message));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
