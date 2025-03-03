package com.crud.all.api.servicos.dto;

import com.crud.all.api.empresa.dto.EmpresaDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class ServicoDTO {
    private UUID uuid;
    private String nome;
    private Double preco;
    private String descricao;
    private EmpresaDTO empresa;

    public ServicoDTO(UUID uuid, String nome, Double preco, String descricao, EmpresaDTO empresa) {
        this.uuid = uuid;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.empresa = empresa;
    }
}
