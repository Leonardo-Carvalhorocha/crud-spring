package com.crud.all.empresa.resporitory;

import com.crud.all.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {
    Optional<Empresa> findById(UUID uuid);
    Optional<Empresa> findByEmail(String email);
}
