package com.project.ediarista.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.ediarista.auth.model.UsuarioAutenticado;
import com.project.ediarista.core.repositories.UsuarioRepository;

public class AutenticacaoService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var mensagem = String.format("Usuário com email %s não encontrado", email);

        return repository.findByEmail(email)
            .map((usuario) -> new UsuarioAutenticado(usuario))
            .orElseThrow(() -> new UsernameNotFoundException(mensagem));
    }
    
    
}
