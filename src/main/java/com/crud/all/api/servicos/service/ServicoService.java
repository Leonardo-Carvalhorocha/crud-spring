package com.crud.all.api.servicos.service;

import com.crud.all.api.clientes.exceptions.ClienteNotFoundException;
import com.crud.all.api.clientes.exceptions.ValidacaoException;
import com.crud.all.api.empresa.service.EmpresaService;
import com.crud.all.api.servicos.dto.ServicoDTO;
import com.crud.all.api.servicos.entity.Servico;
import com.crud.all.api.servicos.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public Page<ServicoDTO> filter(UUID uuidEmpresa, Pageable pageable) {
        Page<Servico> servicos = this.servicoRepository.findByEmpresa_Uuid(uuidEmpresa, pageable);

        Page<ServicoDTO> servicoDTOPage = servicos.map(servico -> new ServicoDTO(
                servico.getUuid(),
                servico.getNome(),
                servico.getPreco(),
                servico.getDescricao(),
                this.empresaService.trasnformEmpresaDTO(servico.getEmpresa().getUuid())
        ));

        return servicoDTOPage;
    }

    public String delete(UUID uuid) {
        Servico servico = this.getServicoByUuid(uuid);
//        if (servico == null) {
//            throw new ClienteNotFoundException("Cliente não encontrado.");
//        }

        this.servicoRepository.delete(servico);
        return"Deletado com sucesso!";
    }

    public ServicoDTO editar(UUID uuid, Servico servicoEditado) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID não pode ser nulo.");
        }

        if (servicoEditado == null) {
            throw new ValidacaoException("Os dados do servico não podem ser nulos");
        }
        Servico servicoAntigo = this.getServicoByUuid(uuid);

        if (servicoAntigo == null) {
            throw new ClienteNotFoundException("Cliente não encontrado.");
        }

        servicoAntigo.setDescricao(servicoEditado.getDescricao());
        servicoAntigo.setNome(servicoEditado.getNome());
        servicoAntigo.setPreco(servicoEditado.getPreco());

        Servico servicoAtualizado = this.servicoRepository.save(servicoAntigo);
        return new ServicoDTO(
                servicoAtualizado.getUuid(),
                servicoAtualizado.getNome(),
                servicoAtualizado.getPreco(),
                servicoAtualizado.getDescricao(),
                this.empresaService.trasnformEmpresaDTO(servicoAtualizado.getEmpresa().getUuid())
        );
    }

    public Servico getServicoByUuid(UUID uuid) {
        Servico servico = this.servicoRepository.getReferenceById(uuid);

        if(servico == null) {
            throw new ClienteNotFoundException();
        }

        return servico;
    }

}
