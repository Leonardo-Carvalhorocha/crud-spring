package com.crud.all.api.clientes.service;

import com.crud.all.api.clientes.dto.ClienteDTO;
import com.crud.all.api.clientes.entities.Cliente;
import com.crud.all.api.clientes.exceptions.ClienteJaExisteException;
import com.crud.all.api.clientes.exceptions.ClienteNotFoundException;
import com.crud.all.api.clientes.exceptions.EmailInvalidoException;
import com.crud.all.api.clientes.exceptions.ValidacaoException;
import com.crud.all.api.empresa.dto.EmpresaDTO;
import com.crud.all.api.empresa.service.EmpresaService;
import com.crud.all.infra.security.TokenService;
import com.crud.all.api.clientes.repository.ClienteRepository;
import com.crud.all.utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmpresaService empresaService;


    public ClienteDTO create(Cliente cliente) {
       if(this.clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new ClienteJaExisteException("Já existe cliente cadastrado com este email");
       }

        if (cliente.getEmpresa() == null || cliente.getEmpresa().getUuid() == null) {
            throw new ValidacaoException("Empresa associada ao cliente é inválida.");
        }

        if(!EmailValidator.isValid(cliente.getEmail())) {
            throw new EmailInvalidoException("O email informado é inválido");
        }

        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));

        Cliente clienteNovo = this.clienteRepository.save(cliente);
        EmpresaDTO empresaDTO = this.empresaService.trasnformEmpresaDTO(clienteNovo.getEmpresa().getUuid());

        return new ClienteDTO(
                clienteNovo.getUuid(),
                clienteNovo.getNome(),
                clienteNovo.getEmail(),
                clienteNovo.getContato(),
                empresaDTO
        );
    }

    public List<ClienteDTO> pageable(UUID uuidEmpresa, Pageable pageable) {
        EmpresaDTO empresa = this.empresaService.trasnformEmpresaDTO(uuidEmpresa);

        if(empresa == null) {
            throw new ClienteNotFoundException("Essa empresa não existe");
        }

        Page<Cliente> clientes = this.clienteRepository.findByEmpresa_Uuid(uuidEmpresa, pageable);
        System.out.println(clientes);
        List<ClienteDTO> clienteDTOS;
        if(clientes.isEmpty()) {
            throw new ClienteNotFoundException("Não existem clientes desta empresa");
        }

        clienteDTOS = clientes.getContent().stream().map(
                cliente -> new ClienteDTO(
                        cliente.getUuid(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getContato(),
                        empresa
                )).toList();

        return clienteDTOS;
    }


    public ClienteDTO editar(UUID uuid, Cliente clienteEditado) {

        if (uuid == null) {
            throw new IllegalArgumentException("UUID não pode ser nulo.");
        }

        if (clienteEditado == null) {
            throw new ValidacaoException("Os dados do cliente a serem editados não podem ser nulos.");
        }
        Cliente clienteAntigo = this.getClienteByUuid(uuid);

        if (clienteAntigo == null) {
            throw new ClienteNotFoundException("Cliente não encontrado.");
        }

        if (clienteEditado.getEmail() == null || clienteEditado.getEmail().trim().isEmpty()) {
            throw new ValidacaoException("O e-mail do cliente é obrigatório.");
        }

        if(!EmailValidator.isValid(clienteEditado.getEmail())) {
            throw new EmailInvalidoException("O email informado é inválido");
        }

        clienteAntigo.setContato(clienteEditado.getContato());
        clienteAntigo.setPassword(clienteEditado.getPassword());
        clienteAntigo.setNome(clienteEditado.getNome());
        clienteAntigo.setEmail(clienteEditado.getEmail());

        Cliente clienteAtualizado = this.clienteRepository.save(clienteAntigo);
        return new ClienteDTO(
                clienteAtualizado.getUuid(),
                clienteAtualizado.getNome(),
                clienteAtualizado.getEmail(),
                clienteAtualizado.getContato(),
                this.empresaService.trasnformEmpresaDTO(clienteAtualizado.getEmpresa().getUuid())
        );
    }

    public ResponseEntity<String> delete(UUID uuid) {
        Cliente cliente = this.getClienteByUuid(uuid);
        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente não encontrado.");
        }

        this.clienteRepository.delete(cliente);
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
    }

    public Cliente getClienteByUuid(UUID uuid) {
        Cliente cliente = this.clienteRepository.getReferenceById(uuid);

        if(cliente == null) {
            throw new ClienteNotFoundException();
        }

        return cliente;
    }

}
