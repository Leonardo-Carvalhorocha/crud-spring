package com.crud.all.funcionarios.controller;

import com.crud.all.empresa.dto.ResponseDTO;
import com.crud.all.funcionarios.dto.FuncionarioDTO;
import com.crud.all.funcionarios.dto.RequestFuncionario;
import com.crud.all.funcionarios.entity.Funcionario;
import com.crud.all.funcionarios.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<RequestFuncionario> create(@RequestBody Funcionario funcionario) {
        try {
            FuncionarioDTO funcionarioDTO = this.funcionarioService.create(funcionario);
            return ResponseEntity.status(HttpStatus.OK).body(new RequestFuncionario(funcionarioDTO, "Funcionario criado com sucesso"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{uuidEmpresa}")
    public ResponseEntity<List<FuncionarioDTO>> filter(@PathVariable UUID uuidEmpresa) {
        try {
            List<FuncionarioDTO> funcionarioDTOS = this.funcionarioService.filter(uuidEmpresa);
            return ResponseEntity.status(HttpStatus.OK).body(funcionarioDTOS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{uuidFuncionario}")
    public ResponseEntity<RequestFuncionario> editar(@PathVariable UUID uuidFuncionario, @RequestBody Funcionario funcionario) {
        try {
            FuncionarioDTO funcionarioDTO = this.funcionarioService.editar(uuidFuncionario, funcionario);
            return ResponseEntity.status(HttpStatus.OK).body(new RequestFuncionario(funcionarioDTO, "Funcionario editado com sucesso"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
