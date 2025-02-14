package com.crud.all.infra.security;

import com.crud.all.empresa.entity.Empresa;
import com.crud.all.empresa.resporitory.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private EmpresaRepository empresaRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empresa user = this.empresaRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Empresa not found"));
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
