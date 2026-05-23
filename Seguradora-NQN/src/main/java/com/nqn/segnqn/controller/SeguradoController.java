package com.nqn.segnqn.controller;

import com.nqn.segnqn.dto.SeguradoRequestDTO;
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
    public ResponseEntity<Segurado> cadastrar(@RequestBody @Valid SeguradoRequestDTO dto) {
        Segurado novoSegurado = seguradoService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoSegurado);
    }

    @GetMapping
    public ResponseEntity<List<Segurado>> listarSegurados(){
        List<Segurado> segurados = seguradoService.listarSegurados();

        return ResponseEntity.ok(segurados);
    }
}
