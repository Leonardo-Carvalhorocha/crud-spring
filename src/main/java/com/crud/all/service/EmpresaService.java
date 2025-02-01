package com.crud.all.service;

import com.crud.all.dto.EmpresaDTO;
import com.crud.all.entities.Empresa;
import com.crud.all.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

        EmpresaDTO empresaDTO = this.trasnformEmpresaDTO(empresaSalva.getUuid());

        return empresaDTO;
    }


    public List<EmpresaDTO> getAll() {
        List<EmpresaDTO> empresas = this.empresaRepository.findAll()
                                    .stream()
                                    .map(empresa -> this.trasnformEmpresaDTO(empresa.getUuid()))
                                    .collect(Collectors.toList());
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

    public ResponseEntity<EmpresaDTO> editar (UUID uuid, Empresa empresaEditada) {
        Optional<Empresa> empresaExistente = empresaRepository.findById(uuid);

        if(empresaExistente.isPresent()) {
            empresaExistente.get().setTelefone(empresaEditada.getTelefone());
            empresaExistente.get().setPassword(empresaEditada.getPassword());
            empresaExistente.get().setCnpj(empresaEditada.getCnpj());
            empresaExistente.get().setNome(empresaEditada.getNome());
            empresaExistente.get().setEmail(empresaEditada.getEmail());
            empresaExistente.get().setEndereco(empresaEditada.getEndereco());

            this.empresaRepository.save(empresaExistente.get());

            return ResponseEntity.ok(this.trasnformEmpresaDTO(empresaExistente.get().getUuid()));
        } else {
            return ResponseEntity.ok(new EmpresaDTO());
        }
    }

    public EmpresaDTO trasnformEmpresaDTO(UUID uuid) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(uuid);
        EmpresaDTO empresaDTO = new EmpresaDTO();

        if(empresaOptional.isPresent()) {
            empresaDTO.setUuid(empresaOptional.get().getUuid());
            empresaDTO.setCnpj(empresaOptional.get().getCnpj());
            empresaDTO.setNome(empresaOptional.get().getNome());
            empresaDTO.setTelefone(empresaOptional.get().getTelefone());
            empresaDTO.setEndereco(empresaOptional.get().getEndereco());
            empresaDTO.setEmail(empresaOptional.get().getEmail());

            return empresaDTO;
        } else {
            return empresaDTO;
        }

    }

}
