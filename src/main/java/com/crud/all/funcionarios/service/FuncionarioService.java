package com.crud.all.funcionarios.service;


import com.crud.all.empresa.dto.EmpresaDTO;
import com.crud.all.funcionarios.dto.FuncionarioDTO;
import com.crud.all.funcionarios.entity.Funcionario;
import com.crud.all.funcionarios.respository.FuncionarioRepository;
import com.crud.all.empresa.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    EmpresaService empresaService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<FuncionarioDTO> create(Funcionario funcionario) {
        funcionario.setPassword(this.passwordEncoder.encode(funcionario.getPassword()));
        Funcionario funcionarioNovo = this.funcionarioRepository.save(funcionario);
        EmpresaDTO empresaDTO = this.empresaService.trasnformEmpresaDTO(funcionario.getEmpresa().getUuid());

        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(
                funcionarioNovo.getUuid(),
                funcionarioNovo.getNome(),
                funcionarioNovo.getEmail(),
                funcionarioNovo.getRole(),
                empresaDTO
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioDTO);
    }

    public ResponseEntity<List<FuncionarioDTO>> filter(UUID uuidEmpresa) {
        List<Funcionario> funcionarios = this.funcionarioRepository.findByEmpresa_Uuid(uuidEmpresa);
        EmpresaDTO empresa = this.empresaService.trasnformEmpresaDTO(uuidEmpresa);

        List<FuncionarioDTO> funcionariosDTO;
        if(!funcionarios.isEmpty()) {
            funcionariosDTO = funcionarios
                              .stream()
                              .map(funcionario -> new FuncionarioDTO(
                                                                      funcionario.getUuid(),
                                                                      funcionario.getNome(),
                                                                      funcionario.getEmail(),
                                                                      funcionario.getRole(),
                                                                      empresa
                                                              )).collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(funcionariosDTO);
        } else {
            funcionariosDTO = List.of();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(funcionariosDTO);
        }
    }

    public ResponseEntity<String> delete(UUID uuid) {
        Optional<Funcionario> funcionario = this.funcionarioRepository.findById(uuid);

        if(funcionario.isPresent()) {
            this.funcionarioRepository.delete(funcionario.get());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario não encotrado");
        }
    }

    public ResponseEntity<FuncionarioDTO> getByUuid(UUID uuid) {
        Optional<Funcionario> funcionario = this.funcionarioRepository.findById(uuid);

        if(funcionario.isPresent()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.transformFuncionarioDTO(funcionario.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FuncionarioDTO());
        }
    }

    public ResponseEntity<FuncionarioDTO> editar(UUID uuid, Funcionario funcionarioEditado) {
        Optional<Funcionario> funcionarioExistente = this.funcionarioRepository.findById(uuid);

        if(funcionarioExistente.isPresent()) {
            Funcionario funcionario = funcionarioExistente.get();

            funcionario.setEmpresa(funcionarioEditado.getEmpresa());
            funcionario.setEmail(funcionarioEditado.getEmail());
            funcionario.setRole(funcionarioEditado.getRole());
            funcionario.setNome(funcionarioEditado.getNome());

            Funcionario funcionarioSalvo = this.funcionarioRepository.save(funcionario);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.transformFuncionarioDTO(funcionarioSalvo));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FuncionarioDTO());
        }
    }

    private FuncionarioDTO transformFuncionarioDTO(Funcionario funcionario) {
         EmpresaDTO empresa = this.empresaService.trasnformEmpresaDTO(funcionario.getEmpresa().getUuid());
        return new FuncionarioDTO(
                funcionario.getUuid(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getRole(),
                empresa
        );
    }

}
