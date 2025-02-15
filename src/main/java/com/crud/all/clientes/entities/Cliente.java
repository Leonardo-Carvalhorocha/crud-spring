package com.crud.all.clientes.entities;

import com.crud.all.empresa.entity.Empresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String nome;
    private String email;
    private String contato;
    private String password;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
}
