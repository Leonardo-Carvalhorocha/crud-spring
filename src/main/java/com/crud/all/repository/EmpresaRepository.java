package com.crud.all.repository;

import com.crud.all.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {
    Optional<Empresa> findById(UUID uuid);
    Optional<Empresa> findByEmail(String email);
}
