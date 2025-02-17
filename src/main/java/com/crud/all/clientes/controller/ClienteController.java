package com.crud.all.clientes.controller;

import com.crud.all.clientes.dto.ClienteDTO;
import com.crud.all.clientes.entities.Cliente;
import com.crud.all.infra.security.SecurityConfig;
import com.crud.all.response.GenericResponse;
import com.crud.all.clientes.service.ClienteService;
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
@RequestMapping("api/clientes")
@Tag(name = "Cliente", description = "Controller para crud de cliente")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Operation(summary = "Criar um cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisção inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<GenericResponse<ClienteDTO, Object>> create(@RequestBody Cliente cliente) {
        ClienteDTO clienteCreate = this.clienteService.create(cliente);
        GenericResponse<ClienteDTO, Object> response = new GenericResponse<>("Cliente criado com sucesso", clienteCreate);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    };

    @Operation(summary = "Listar todos os clientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "400", description = "Requisção inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{uuidEmpresa}")
    public ResponseEntity<List<ClienteDTO>> getAll(@PathVariable UUID uuidEmpresa) {
        try {
            List<ClienteDTO> clienteDTOS = this.clienteService.getClientesPerEmpresa(uuidEmpresa);
            return  ResponseEntity.status(HttpStatus.OK).body(clienteDTOS);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Atualizar um cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente editado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisção inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{uuidCliente}")
    public ResponseEntity<String> editar(@PathVariable UUID uuidCliente, @RequestBody Cliente cliente) {
        Cliente clienteEditado = this.clienteService.editar(uuidCliente, cliente);
        return ResponseEntity.status(HttpStatus.OK).body("Cliente editado com sucesso");
    }

    @Operation(summary = "Deletar um cliente por empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisção inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{uuidCliente}")
    public ResponseEntity<String> delete(@PathVariable UUID uuidCliente) {
        String message = this.clienteService.delete(uuidCliente);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
