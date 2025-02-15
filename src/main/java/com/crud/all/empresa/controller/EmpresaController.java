package com.crud.all.empresa.controller;

import com.crud.all.empresa.dto.EmpresaDTO;
import com.crud.all.empresa.dto.ResponseDTO;
import com.crud.all.empresa.entity.Empresa;
import com.crud.all.empresa.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/empresas")
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

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
