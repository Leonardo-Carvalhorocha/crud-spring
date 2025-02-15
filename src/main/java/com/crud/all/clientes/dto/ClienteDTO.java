package com.crud.all.clientes.dto;

import com.crud.all.empresa.dto.EmpresaDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class ClienteDTO {
    private UUID uuid;
    private String nome;
    private String email;
    private String contato;
    private EmpresaDTO empresa;

    public ClienteDTO() {};

    public ClienteDTO(UUID uuid, String nome, String email, String contato, EmpresaDTO empresa) {
        this.uuid = uuid;
        this.nome = nome;
        this.email = email;
        this.contato = contato;
        this.empresa = empresa;
    }
}
