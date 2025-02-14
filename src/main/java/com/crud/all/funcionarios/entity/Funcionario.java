package com.crud.all.funcionarios.entity;

import com.crud.all.funcionarios.enums.Role;
import com.crud.all.empresa.entity.Empresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "funcionario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String nome;
    private String email;
    private String password;
    private Role role;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

}
