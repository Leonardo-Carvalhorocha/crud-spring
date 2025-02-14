package com.crud.all.funcionarios.respository;

import com.crud.all.funcionarios.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    List<Funcionario> findByEmpresa_Uuid(UUID empresaUuid);
}
