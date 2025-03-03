package com.crud.all.api.empresa.resporitory;

import com.crud.all.api.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {
    Optional<Empresa> findById(UUID uuid);

    Optional<Empresa> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Empresa e WHERE e.cnpj = :cnpj")
    boolean cnpjExists(@Param("cnpj") String cnpj);

    // Verifica se um email já existe
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Empresa e WHERE e.email = :email")
    boolean emailExists(@Param("email") String email);
}
