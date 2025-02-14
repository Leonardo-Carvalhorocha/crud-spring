package com.crud.all.funcionarios.controller;

import com.crud.all.funcionarios.dto.FuncionarioDTO;
import com.crud.all.funcionarios.entity.Funcionario;
import com.crud.all.funcionarios.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/funcionarios")
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @PostMapping()
    public ResponseEntity<FuncionarioDTO> create(@RequestBody Funcionario funcionario) {
        return this.funcionarioService.create(funcionario);
    }

    @GetMapping("/{uuidEmpresa}")
    public ResponseEntity<List<FuncionarioDTO>> filter(@PathVariable UUID uuidEmpresa) {
        return this.funcionarioService.filter(uuidEmpresa);
    }

    @PutMapping("/{uuidFuncionario}")
    public ResponseEntity<FuncionarioDTO> editar(@PathVariable UUID uuidFuncionario, @RequestBody Funcionario funcionario) {
        return this.funcionarioService.editar(uuidFuncionario, funcionario);
    }

    @DeleteMapping("/{uuidFuncionario}")
    public ResponseEntity<String> deletar(@PathVariable UUID uuidFuncionario) {
        return this.funcionarioService.delete(uuidFuncionario);
    }
}
