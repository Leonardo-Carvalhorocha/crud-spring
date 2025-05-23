package com.crud.all.api.funcionarios.dto;

import com.crud.all.api.empresa.dto.EmpresaDTO;
import com.crud.all.api.funcionarios.enums.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class FuncionarioDTO {
    private UUID uuid;
    private String nome;
    private String email;
    private Role role;
    private EmpresaDTO empresa;

    public FuncionarioDTO() {};

    public FuncionarioDTO(UUID uuid, String nome, String email, Role role, EmpresaDTO empresa) {
        this.uuid = uuid;
        this.nome = nome;
        this.email = email;
        this.role = role;
        this.empresa = empresa;
    }
}
