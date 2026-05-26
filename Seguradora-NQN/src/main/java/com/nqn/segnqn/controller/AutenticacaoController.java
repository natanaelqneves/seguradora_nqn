package com.nqn.segnqn.controller;

import com.nqn.segnqn.dto.AutenticacaoDTO;
import com.nqn.segnqn.dto.TokenResponseDTO;
import com.nqn.segnqn.service.AutenticacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AutenticacaoController {

    private AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> autenticar(@RequestBody @Valid AutenticacaoDTO dto) {
        String token = autenticacaoService.login(dto);

        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}
