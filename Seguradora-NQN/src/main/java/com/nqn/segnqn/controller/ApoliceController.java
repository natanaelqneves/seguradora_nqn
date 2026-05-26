package com.nqn.segnqn.controller;

import com.nqn.segnqn.dto.ApoliceRequestDTO;
import com.nqn.segnqn.dto.ApoliceResponseDTO;
import com.nqn.segnqn.model.Apolice;
import com.nqn.segnqn.model.Corretor;
import com.nqn.segnqn.service.ApoliceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apolices")
public class ApoliceController {

    private final ApoliceService apoliceService;

    public ApoliceController(ApoliceService apoliceService) {
        this.apoliceService = apoliceService;
    }

    @PostMapping
    public ResponseEntity<ApoliceResponseDTO> emitirApolice(
            @RequestBody @Valid ApoliceRequestDTO dto,
            @AuthenticationPrincipal Corretor corretorLogado) {
        ApoliceResponseDTO novaApolice = apoliceService.emitirApolice(dto, corretorLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaApolice);
    }

    @GetMapping
    public ResponseEntity<List<ApoliceResponseDTO>> listarTodas() {
        List<ApoliceResponseDTO> apolices = apoliceService.listarApolices();
        return ResponseEntity.ok(apolices);
    }
}
