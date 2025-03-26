package com.crud.all.api.clientes.controller;

import com.crud.all.api.clientes.dto.ClienteDTO;
import com.crud.all.api.clientes.dto.RequestCliente;
import com.crud.all.api.clientes.entities.Cliente;
import com.crud.all.infra.security.SecurityConfig;
import com.crud.all.response.GenericResponse;
import com.crud.all.api.clientes.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
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
    @PostMapping
    public ResponseEntity<GenericResponse<ClienteDTO, Object>> create(@RequestBody Cliente cliente) {
        ClienteDTO clienteCreate = this.clienteService.create(cliente);
        GenericResponse<ClienteDTO, Object> response = new GenericResponse<>("Cliente criado com sucesso", clienteCreate);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    };

    @Operation(summary = "Listar todos os clientes")
    @GetMapping("/{uuidEmpresa}")
        public ResponseEntity<Page<ClienteDTO>> getAll(@PathVariable UUID uuidEmpresa, Pageable pageable){
        Page<ClienteDTO> clienteDTOS = this.clienteService.pageable(uuidEmpresa, pageable);
        return  ResponseEntity.status(HttpStatus.OK).body(clienteDTOS);
    }

    @Operation(summary = "Atualizar um cliente")
    @PutMapping("/{uuidCliente}")
    public ResponseEntity<RequestCliente> editar(@PathVariable UUID uuidCliente, @RequestBody Cliente cliente) {
        ClienteDTO clienteEditado = this.clienteService.editar(uuidCliente, cliente);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestCliente(clienteEditado, "Cliente editado com sucesso!"));
    }

    @Operation(summary = "Deletar um cliente por empresa")
    @DeleteMapping("/{uuidCliente}")
    public ResponseEntity<RequestCliente> delete(@PathVariable UUID uuidCliente) {
        this.clienteService.delete(uuidCliente);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestCliente(null, "Cliente deletado com sucesso!"));
    }

}
