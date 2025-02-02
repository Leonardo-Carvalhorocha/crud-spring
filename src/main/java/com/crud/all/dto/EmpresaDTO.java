package com.crud.all.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.UUID;

@Data
public class EmpresaDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID uuid;
    private String nome;
    private String cnpj;
    private String endereco;
    private String telefone;
    private String email;
    private String username;

    public EmpresaDTO(){};

    public EmpresaDTO(String nome, String cnpj, String endereco, String telefone, String email, UUID uuid, String username) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.uuid = uuid;
        this.username = username;
    }
}
