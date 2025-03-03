package com.crud.all.api.servicos.service;

import com.crud.all.api.empresa.service.EmpresaService;
import com.crud.all.api.servicos.dto.ServicoDTO;
import com.crud.all.api.servicos.entity.Servico;
import com.crud.all.api.servicos.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServicoService {

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    EmpresaService empresaService;

    public ServicoDTO create(Servico servico) {
        Servico servicoCreate = this.servicoRepository.save(servico);
       return new ServicoDTO(
                servicoCreate.getUuid(),
                servicoCreate.getNome(),
                servicoCreate.getPreco(),
                servicoCreate.getDescricao(),
                this.empresaService.trasnformEmpresaDTO(servico.getEmpresa().getUuid())
        );
    }

    public void filter(UUID uuidEmpresa, Pageable pageable) {
        Page<Servico> servicoPage = this.servicoRepository.findByEmpresa_Uuid(uuidEmpresa, pageable);
        System.out.println(servicoPage);
    }

}
