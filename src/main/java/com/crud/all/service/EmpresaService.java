package com.crud.all.service;

import com.crud.all.dto.EmpresaDTO;
import com.crud.all.entities.Empresa;
import com.crud.all.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    public EmpresaDTO create(Empresa empresa) {
        Empresa empresaSalva = empresaRepository.save(empresa);

        EmpresaDTO empresaDTO = new EmpresaDTO(
                empresaSalva.getNome(),
                empresaSalva.getCnpj(),
                empresaSalva.getEndereco(),
                empresaSalva.getTelefone(),
                empresaSalva.getEmail(),
                empresaSalva.getUuid()
        );

        return empresaDTO;
    }


    public List<EmpresaDTO> getAll() {
        // coverte cada empresa em empresaDTO e retorna uma lista
        List<EmpresaDTO> empresas = this.empresaRepository.findAll()
                                    .stream()
                                    .map(empresa -> new EmpresaDTO(
                                            empresa.getNome(),
                                            empresa.getCnpj(),
                                            empresa.getEndereco(),
                                            empresa.getTelefone(),
                                            empresa.getEmail(),
                                            empresa.getUuid()
                                    )).collect(Collectors.toList());
            System.out.println(empresas);
        return empresas;
    }

    public ResponseEntity<EmpresaDTO> getByUuid(UUID uuid) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(uuid);

        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            EmpresaDTO empresaDTO = new EmpresaDTO(
                    empresa.getNome(),
                    empresa.getCnpj(),
                    empresa.getEndereco(),
                    empresa.getTelefone(),
                    empresa.getEmail(),
                    empresa.getUuid()
            );
            return ResponseEntity.ok(empresaDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public ResponseEntity<String> delete(UUID uuid) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(uuid);

        if (empresaOptional.isPresent()) {
           this.empresaRepository.deleteById(uuid);
           return ResponseEntity.ok("Empresa deletada com sucesso!");
        } else {
            throw new EntityNotFoundException("Empresa não encontrada com UUID: " + uuid);
        }
    }

}
