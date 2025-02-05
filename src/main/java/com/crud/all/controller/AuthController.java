package com.crud.all.controller;

import com.crud.all.dto.EmpresaDTO;
import com.crud.all.dto.LoginRequestDTO;
import com.crud.all.dto.RegisterRequestDTO;
import com.crud.all.dto.ResponseDTO;
import com.crud.all.entities.Empresa;
import com.crud.all.infra.security.TokenService;
import com.crud.all.repository.EmpresaRepository;
import com.crud.all.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
//    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EmpresaService empresaService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        Optional<Empresa> empresa = this.empresaService.empresaByEmail(body.email());

        if(!empresa.isEmpty()) {
            Empresa empresaExiste = empresa.get();
            EmpresaDTO empresaDTO = this.empresaService.trasnformEmpresaDTO(empresa.get().getUuid());

            if(passwordEncoder.matches(body.password(), empresaExiste.getPassword())){
                String token = this.tokenService.generateToken(empresaExiste);
                return ResponseEntity.ok(new ResponseDTO(token, empresaDTO));
            }
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Empresa body){
        Optional<Empresa> empresa = this.empresaService.empresaByEmail(body.getEmail());

        if(empresa.isEmpty()) {
            return this.empresaService.create(body);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Já existe essa empresa cadastrada com esse email");
    }


}
