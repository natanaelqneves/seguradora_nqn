package com.nqn.segnqn.controller;

import com.nqn.segnqn.dto.CorretorRequestDTO;
import com.nqn.segnqn.dto.CorretorResponseDTO;
import com.nqn.segnqn.dto.SeguradoRequestDTO;
import com.nqn.segnqn.dto.SeguradoResponseDTO;
import com.nqn.segnqn.service.CorretorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/corretores")
public class CorretorController {


    private CorretorService corretorService;

    public CorretorController(CorretorService corretorService) {
        this.corretorService = corretorService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<CorretorResponseDTO> cadastrar(@RequestBody @Valid CorretorRequestDTO dto) {
        CorretorResponseDTO novoCorretor = corretorService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCorretor);
    }

    @GetMapping
    public ResponseEntity<List<CorretorResponseDTO>> listarTodos() {
        List<CorretorResponseDTO> corretores = corretorService.listarCorretores();
        return ResponseEntity.ok(corretores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorretorResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CorretorRequestDTO dto) {
        CorretorResponseDTO corretorAtualizado = corretorService.atualizar(id, dto);
        return ResponseEntity.ok(corretorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        corretorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
