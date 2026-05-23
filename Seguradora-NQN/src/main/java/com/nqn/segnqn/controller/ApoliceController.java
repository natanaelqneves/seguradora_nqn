package com.nqn.segnqn.controller;

import com.nqn.segnqn.dto.ApoliceRequestDTO;
import com.nqn.segnqn.model.Apolice;
import com.nqn.segnqn.service.ApoliceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<Apolice> emitirApolice(@RequestBody @Valid ApoliceRequestDTO dto){
        Apolice novaApolice = apoliceService.emitirApolice(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaApolice);
    }

    @GetMapping
    public ResponseEntity<List<Apolice>> listarApolices(){
        List<Apolice> apolices = apoliceService.listarApolices();

        return ResponseEntity.ok(apolices);
    }
}
