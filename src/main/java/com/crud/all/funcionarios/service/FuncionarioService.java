package com.crud.all.funcionarios.service;


import com.crud.all.empresa.dto.EmpresaDTO;
import com.crud.all.funcionarios.dto.FuncionarioDTO;
import com.crud.all.funcionarios.entity.Funcionario;
import com.crud.all.funcionarios.exceptions.FuncionarioCampoObrigatorioException;
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

    public FuncionarioDTO create(Funcionario funcionario) {
        if(funcionario.getEmpresa() == null) throw new FuncionarioCampoObrigatorioException("Empresa é obrigatório");
        if(funcionario.getRole() == null) throw new FuncionarioCampoObrigatorioException("Permissão é obrigatório");
        if(funcionario.getNome() == null) throw new FuncionarioCampoObrigatorioException("Nome é obrigatório");
        if(funcionario.getEmail() == null) throw new FuncionarioCampoObrigatorioException("Email é obrigatório");
        if(funcionario.getPassword() == null) throw new FuncionarioCampoObrigatorioException("Senha é obrigatório");
        if(funcionario.getPassword().length() < 8) throw new FuncionarioCampoObrigatorioException("Senha deve conter no mínimo 8 caracteres");

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

        return funcionarioDTO;
    }

    public List<FuncionarioDTO> filter(UUID uuidEmpresa) {
        List<Funcionario> funcionarios = this.funcionarioRepository.findByEmpresa_Uuid(uuidEmpresa);
        EmpresaDTO empresa = this.empresaService.trasnformEmpresaDTO(uuidEmpresa);

        List<FuncionarioDTO> funcionariosDTO;
        if(!funcionarios.isEmpty()) {
            funcionariosDTO = funcionarios
                              .stream()
                              .map(funcionario ->
                                      new FuncionarioDTO(
                                              funcionario.getUuid(),
                                              funcionario.getNome(),
                                              funcionario.getEmail(),
                                              funcionario.getRole(),
                                              empresa
                                      )).collect(Collectors.toList());

            return funcionariosDTO;
        } else {
            funcionariosDTO = List.of();
            return funcionariosDTO;
        }
    }

    public String delete(UUID uuid) {
        Optional<Funcionario> funcionario = this.funcionarioRepository.findById(uuid);

        if(funcionario.isPresent()) {
            this.funcionarioRepository.delete(funcionario.get());
            return"Deletado com sucesso!";
        }
            return "Funcionario não encotrado";
    }

    public FuncionarioDTO getByUuid(UUID uuid) {
        Optional<Funcionario> funcionario = this.funcionarioRepository.findById(uuid);

        if(funcionario.isPresent()) {
            return this.transformFuncionarioDTO(funcionario.get());
        } else {
            return new FuncionarioDTO();
        }
    }

    public FuncionarioDTO editar(UUID uuid, Funcionario funcionarioEditado) {
        Optional<Funcionario> funcionarioExistente = this.funcionarioRepository.findById(uuid);

        if(funcionarioExistente.isPresent()) {
            Funcionario funcionario = funcionarioExistente.get();

            funcionario.setEmpresa(funcionarioEditado.getEmpresa());
            funcionario.setEmail(funcionarioEditado.getEmail());
            funcionario.setRole(funcionarioEditado.getRole());
            funcionario.setNome(funcionarioEditado.getNome());

            Funcionario funcionarioSalvo = this.funcionarioRepository.save(funcionario);

            return this.transformFuncionarioDTO(funcionarioSalvo);
        } else {
            return new FuncionarioDTO();
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
