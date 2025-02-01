package com.crud.all.repository;

import com.crud.all.dto.FuncionarioDTO;
import com.crud.all.entities.Empresa;
import com.crud.all.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    List<Funcionario> findByEmpresa_Uuid(UUID empresaUuid);
}
