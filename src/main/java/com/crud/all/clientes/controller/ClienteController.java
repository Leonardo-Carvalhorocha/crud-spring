package com.crud.all.clientes.controller;

import com.crud.all.clientes.dto.ClienteDTO;
import com.crud.all.clientes.entities.Cliente;
import com.crud.all.response.GenericResponse;
import com.crud.all.clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping
    public ResponseEntity<GenericResponse<ClienteDTO, Object>> create(Cliente cliente) {
        ClienteDTO clienteCreate = this.clienteService.create(cliente);
        GenericResponse<ClienteDTO, Object> response = new GenericResponse<>("Cliente criado com sucesso", clienteCreate);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    };

    @GetMapping("/{uuidEmpresa}")
    public ResponseEntity<List<ClienteDTO>> getAll(@PathVariable UUID uuidEmpresa) {
        try {
            List<ClienteDTO> clienteDTOS = this.clienteService.getClientesPerEmpresa(uuidEmpresa);
            return  ResponseEntity.status(HttpStatus.OK).body(clienteDTOS);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
