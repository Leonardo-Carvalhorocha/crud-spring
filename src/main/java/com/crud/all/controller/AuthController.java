package com.crud.all.controller;

import com.crud.all.dto.LoginRequestDTO;
import com.crud.all.dto.RegisterRequestDTO;
import com.crud.all.dto.ResponseDTO;
import com.crud.all.entities.Empresa;
import com.crud.all.infra.security.TokenService;
import com.crud.all.repository.EmpresaRepository;
import com.crud.all.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EmpresaService empresaService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        Optional<Empresa> empresa = this.empresaService.empresaByEmail(body.email());
        System.out.println(empresa.get().getPassword());
        System.out.println(body.password());
        if(!empresa.isEmpty()) {
            Empresa empresaExiste = empresa.get();
            if(passwordEncoder.matches(body.password(), empresaExiste.getPassword())){
                String token = this.tokenService.generateToken(empresaExiste);
                return ResponseEntity.ok(new ResponseDTO(empresaExiste.getNome(), token));
            }
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Empresa body){
        Optional<Empresa> user = this.empresaRepository.findByEmail(body.getEmail());

        if(user.isEmpty()) {
            return this.empresaService.create(body);
//            return ResponseEntity.ok(new ResponseDTO(newUser.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
