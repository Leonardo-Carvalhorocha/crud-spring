package com.crud.all.api.clientes.repository;

import com.crud.all.api.clientes.entities.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Page<Cliente> findByEmpresa_Uuid(UUID uuidEmpresa, Pageable pageable);

    boolean existsByEmail(@NotBlank(message = "O e-mail é obrigatório") @Email(message = "E-mail inválido") String email);
}
