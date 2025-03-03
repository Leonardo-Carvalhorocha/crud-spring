package com.crud.all.api.servicos.repository;

import com.crud.all.api.servicos.entity.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, UUID> {
    Page<Servico> findByEmpresa_Uuid(UUID empresaUuid, Pageable pageable);
}
