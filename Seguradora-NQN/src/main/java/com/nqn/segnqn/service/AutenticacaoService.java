package com.nqn.segnqn.service;

import com.nqn.segnqn.dto.AutenticacaoDTO;
import com.nqn.segnqn.repository.CorretorRepository;
import com.nqn.segnqn.repository.UsuarioRepository;
import com.nqn.segnqn.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    private UsuarioRepository usuarioRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public AutenticacaoService(UsuarioRepository usuarioRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String login(AutenticacaoDTO dto){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.usuario(),
                            dto.senha())
            );
        } catch (DisabledException e) {
            throw  new RuntimeException("Acesso pendente de liberação pelo Administrador/DBA");
        }

        var usuario = usuarioRepository.findByNomeDeUsuario(dto.usuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        return jwtService.gerarToken(usuario);
    }
}
