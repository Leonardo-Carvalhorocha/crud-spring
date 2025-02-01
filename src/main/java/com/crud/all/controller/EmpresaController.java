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
@RequestMapping("api/empresa")
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

    @PostMapping()
    public ResponseEntity<EmpresaDTO> create(@RequestBody Empresa empresa) {
        EmpresaDTO empresaDTO = empresaService.create(empresa);
        return ResponseEntity.ok(empresaDTO);
    }

    @GetMapping()
    public ResponseEntity<List<EmpresaDTO>> getAll() {
        return ResponseEntity.ok(this.empresaService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EmpresaDTO> get(@PathVariable UUID uuid) {
        return this.empresaService.getByUuid(uuid);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> delete(@PathVariable UUID uuid) {
        return this.empresaService.delete(uuid);
    }
}
