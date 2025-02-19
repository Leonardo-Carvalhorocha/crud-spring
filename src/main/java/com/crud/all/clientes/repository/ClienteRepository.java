package com.crud.all.clientes.repository;

import com.crud.all.clientes.dto.ClienteDTO;
import com.crud.all.clientes.entities.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    List<Cliente> findByEmpresa_Uuid(UUID uuidEmpresa);

    boolean existsByEmail(@NotBlank(message = "O e-mail é obrigatório") @Email(message = "E-mail inválido") String email);
}
