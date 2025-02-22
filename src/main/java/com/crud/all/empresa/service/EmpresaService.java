package com.crud.all.empresa.service;

import com.crud.all.empresa.dto.EmpresaDTO;
import com.crud.all.empresa.dto.ResponseDTO;
import com.crud.all.empresa.entity.Empresa;
import com.crud.all.empresa.exceptions.EmailInvalidoEmpresaException;
import com.crud.all.empresa.exceptions.EmpresaExistenteException;
import com.crud.all.empresa.exceptions.EmpresaNotFoundException;
import com.crud.all.infra.security.TokenService;
import com.crud.all.empresa.resporitory.EmpresaRepository;
import com.crud.all.utils.EmailValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseDTO create(Empresa empresa) {
        if(this.empresaRepository.cnpjExists(empresa.getCnpj())) {
            throw new EmpresaExistenteException("Empresa com o CNPJ: " + empresa.getCnpj() + ", já foi cadastrada.");
        }

        if(this.empresaRepository.emailExists(empresa.getEmail())) {
            throw new EmpresaExistenteException("Esse email já foi cadastrado, por favor, digite outro.");
        }

        if(!EmailValidator.isValid(empresa.getEmail())) {
            throw new EmailInvalidoEmpresaException("O email informado é inválido");
        }

        empresa.setPassword(this.passwordEncoder.encode(empresa.getPassword()));
        Empresa empresaSalva = empresaRepository.save(empresa);

        EmpresaDTO empresaDTO = this.trasnformEmpresaDTO(empresaSalva.getUuid());
        String token = this.tokenService.generateToken(empresaSalva);
        return new ResponseDTO(token, empresaDTO, "Empresa criado com sucesso");
    }


    public List<EmpresaDTO> getAll() {
        List<EmpresaDTO> empresas = this.empresaRepository.findAll()
                                    .stream()
                                    .map(empresa -> this.trasnformEmpresaDTO(empresa.getUuid()))
                                    .collect(Collectors.toList());
        return empresas;
    }

    public EmpresaDTO getByUuid(UUID uuid) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(uuid);

        if(empresaOptional.get() == null) {
            throw new EmpresaNotFoundException("Empresa não existe com o uuid: " + uuid);
        }

        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            EmpresaDTO empresaDTO = new EmpresaDTO(
                    empresa.getNome(),
                    empresa.getCnpj(),
                    empresa.getEndereco(),
                    empresa.getTelefone(),
                    empresa.getEmail(),
                    empresa.getUuid(),
                    empresa.getUsername()
            );
            return empresaDTO;
        } else {
            return null;
        }
    }

    public String delete(UUID uuid) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(uuid)
                ;
        if(empresaOptional.get() == null) {
            throw new EmpresaNotFoundException("Empresa não existe com o uuid: " + uuid);
        }

        if (empresaOptional.isPresent()) {
           this.empresaRepository.deleteById(uuid);
           return "Empresa deletada com sucesso!";
        } else {
            throw new EntityNotFoundException("Empresa não encontrada com UUID: " + uuid);
        }
    }

    public EmpresaDTO editar (UUID uuid, Empresa empresaEditada) {
        Optional<Empresa> empresaExistente = this.empresaRepository.findById(uuid);

        if(empresaExistente.get() == null) {
            throw new EmpresaNotFoundException("Empresa não existe com o uuid: " + uuid);
        }

        if(empresaExistente.isPresent()) {
            empresaExistente.get().setTelefone(empresaEditada.getTelefone());
            empresaExistente.get().setPassword(empresaEditada.getPassword());
            empresaExistente.get().setCnpj(empresaEditada.getCnpj());
            empresaExistente.get().setNome(empresaEditada.getNome());
            empresaExistente.get().setEmail(empresaEditada.getEmail());
            empresaExistente.get().setEndereco(empresaEditada.getEndereco());

            this.empresaRepository.save(empresaExistente.get());

            return this.trasnformEmpresaDTO(empresaExistente.get().getUuid());
        } else {
            return new EmpresaDTO();
        }
    }

    public EmpresaDTO trasnformEmpresaDTO(UUID uuid) {
        Optional<Empresa> empresaOptional = this.empresaRepository.findById(uuid);
        EmpresaDTO empresaDTO = new EmpresaDTO();

        if(empresaOptional.isPresent()) {
            empresaDTO.setUuid(empresaOptional.get().getUuid());
            empresaDTO.setCnpj(empresaOptional.get().getCnpj());
            empresaDTO.setNome(empresaOptional.get().getNome());
            empresaDTO.setTelefone(empresaOptional.get().getTelefone());
            empresaDTO.setEndereco(empresaOptional.get().getEndereco());
            empresaDTO.setEmail(empresaOptional.get().getEmail());
            empresaDTO.setUsername(empresaOptional.get().getUsername());

            return empresaDTO;
        } else {
            return empresaDTO;
        }

    }

    public Optional<Empresa> empresaByEmail(String email) {
        Optional<Empresa> empresa = this.empresaRepository.findByEmail(email);

        if(empresa.isPresent()) {
            return empresa;
        }
        return Optional.of(new Empresa());
    }

    public Optional<Empresa> empresaByUuid(UUID uuid) {
        Optional<Empresa> empresa = this.empresaRepository.findById(uuid);
        if(empresa.isPresent()) {
            return empresa;
        } else {
            return Optional.of(new Empresa());
        }
    }

}
