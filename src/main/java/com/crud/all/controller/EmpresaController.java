package com.crud.all.controller;

import com.crud.all.dto.EmpresaDTO;
import com.crud.all.entities.Empresa;
import com.crud.all.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return this.empresaService.create(empresa);
    }

    @GetMapping()
    public ResponseEntity<List<EmpresaDTO>> getAll() {
        return this.empresaService.getAll();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EmpresaDTO> get(@PathVariable UUID uuid) {
        return this.empresaService.getByUuid(uuid);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> delete(@PathVariable UUID uuid) {
        return this.empresaService.delete(uuid);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<EmpresaDTO> editar(@PathVariable UUID uuid, @RequestBody Empresa empresa) {
        return this.empresaService.editar(uuid, empresa);
    }
}
