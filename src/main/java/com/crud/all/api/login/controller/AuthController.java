package com.crud.all.api.login.controller;

import com.crud.all.api.empresa.dto.EmpresaDTO;
import com.crud.all.api.login.dto.LoginRequestDTO;
import com.crud.all.api.empresa.dto.ResponseDTO;
import com.crud.all.api.empresa.entity.Empresa;
import com.crud.all.api.login.exceptions.InvalidTokenException;
import com.crud.all.infra.security.TokenService;
import com.crud.all.api.empresa.service.EmpresaService;
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
            EmpresaDTO empresaDTO = this.empresaService.trasnformEmpresaDTO(empresaExiste.getUuid());

            if(passwordEncoder.matches(body.password(), empresaExiste.getPassword())){
                String token = this.tokenService.generateToken(empresaExiste);
                return ResponseEntity.ok(new ResponseDTO(token, empresaDTO, "Login realizado com sucesso!"));
            }
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Empresa body){
        Optional<Empresa> empresa = this.empresaService.empresaByEmail(body.getEmail());

        if(empresa.isEmpty()) {
            ResponseDTO responseDTO = this.empresaService.create(body);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Já existe essa empresa cadastrada com esse email");
    }

    @PostMapping("/validatetoken")
    public ResponseEntity<?> validate(@RequestBody String token) {
        try {
            String tokenValido = this.tokenService.validateToken(token);
            return ResponseEntity.ok().body("Token válido! Subject: " + tokenValido);
        } catch (InvalidTokenException e) {
            throw e;
        }
    }



}
