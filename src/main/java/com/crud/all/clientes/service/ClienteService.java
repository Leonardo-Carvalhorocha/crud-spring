package com.crud.all.clientes.service;

import com.crud.all.clientes.dto.ClienteDTO;
import com.crud.all.clientes.entities.Cliente;
import com.crud.all.empresa.dto.EmpresaDTO;
import com.crud.all.empresa.service.EmpresaService;
import com.crud.all.infra.security.TokenService;
import com.crud.all.clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        cliente.setPassword(this.passwordEncoder.encode(cliente.getPassword()));
        Cliente clienteNovo = this.clienteRepository.save(cliente);
        EmpresaDTO empresaDTO = this.empresaService.trasnformEmpresaDTO(cliente.getEmpresa().getUuid());

        ClienteDTO clienteDTO = new ClienteDTO(
                clienteNovo.getUuid(),
                clienteNovo.getNome(),
                clienteNovo.getEmail(),
                clienteNovo.getContato(),
                empresaDTO
        );

        return clienteDTO;
    }

    public List<ClienteDTO> getClientesPerEmpresa(UUID uuidEmpresa) {
        List<Cliente> clientes = this.clienteRepository.findByEmpresa_Uuid(uuidEmpresa);
        EmpresaDTO empresa = this.empresaService.trasnformEmpresaDTO(uuidEmpresa);

        List<ClienteDTO> clienteDTOS;
        if(!clientes.isEmpty()) {
            clienteDTOS = clientes.stream().map(
                    cliente -> new ClienteDTO(
                                cliente.getUuid(),
                                cliente.getNome(),
                                cliente.getEmail(),
                                cliente.getContato(),
                                empresa
                            )).toList();

            return clienteDTOS;
        } else {
            clienteDTOS = List.of();
            return clienteDTOS;
        }
    }

}
