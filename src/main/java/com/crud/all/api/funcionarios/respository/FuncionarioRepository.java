package com.crud.all.api.funcionarios.respository;

import com.crud.all.api.funcionarios.entity.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    Page<Funcionario> findByEmpresa_Uuid(UUID empresaUuid, Pageable pageable);
}
