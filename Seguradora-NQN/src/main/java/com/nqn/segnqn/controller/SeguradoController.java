package com.nqn.segnqn.controller;

import com.nqn.segnqn.dto.SeguradoRequestDTO;
import com.nqn.segnqn.dto.SeguradoResponseDTO;
import com.nqn.segnqn.model.Segurado;
import com.nqn.segnqn.service.SeguradoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/segurados")
public class SeguradoController {

    private final SeguradoService seguradoService;

    public SeguradoController(SeguradoService seguradoService) {
        this.seguradoService = seguradoService;
    }

    @PostMapping
    public ResponseEntity<SeguradoResponseDTO> cadastrar(@RequestBody @Valid SeguradoRequestDTO dto) {
        SeguradoResponseDTO novoSegurado = seguradoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoSegurado);
    }

    @GetMapping
    public ResponseEntity<List<SeguradoResponseDTO>> listarTodos() {
        List<SeguradoResponseDTO> segurados = seguradoService.listarSegurados();
        return ResponseEntity.ok(segurados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeguradoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid SeguradoRequestDTO dto) {
        SeguradoResponseDTO seguradoAtualizado = seguradoService.atualizar(id, dto);
        return ResponseEntity.ok(seguradoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        seguradoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
