package com.crud.all.empresa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "empresa")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    private String cnpj;

    @Column(nullable = false, length = 150)
    private String endereco;

    @Column(length = 15)
    private String telefone;

    private String username;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;
}
