package com.nqn.segnqn.service;

import com.nqn.segnqn.dto.ApoliceRequestDTO;
import com.nqn.segnqn.dto.ApoliceResponseDTO;
import com.nqn.segnqn.model.Apolice;
import com.nqn.segnqn.model.Segurado;
import com.nqn.segnqn.repository.ApoliceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ApoliceServiceTest {

    private ApoliceRepository apoliceRepository;

    private SeguradoService seguradoService;
    private ApoliceService apoliceService;

    @BeforeEach
    void setUp(){
        this.apoliceRepository = mock(ApoliceRepository.class);
        this.seguradoService = mock(SeguradoService.class);

        this.apoliceService = new ApoliceService(apoliceRepository, seguradoService);
    }

    @Test
    @DisplayName("Deve emitir uma apólice com sucesso dob condições válidas")
    void emitirApoliceComSucesso() {

        Segurado seguradoValido = new Segurado(7L, "Segurado Válido", "11122233344", "seguradovalido@email.com");

        ApoliceRequestDTO request = new ApoliceRequestDTO(
                "AP-2026-AUTO",
                new BigDecimal("1000.00"),
                new BigDecimal("50000.00"),
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                7L
        );

        Apolice apoliceSalva = new Apolice(
                1L,
                "AP-2026-AUTO",
                new BigDecimal("1000.00"),
                new BigDecimal("5000.00"),
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                seguradoValido
        );

        when(apoliceRepository.findByNumeroApolice("AP-2026-AUTO")).thenReturn(Optional.empty());
        when(seguradoService.buscarPorId(7L)).thenReturn(seguradoValido);
        when(apoliceRepository.save(any(Apolice.class))).thenReturn(apoliceSalva);

        ApoliceResponseDTO response = apoliceService.emitirApolice(request);

        assertNotNull(response);
        assertEquals("AP-2026-AUTO", response.numeroApolice());
        assertEquals("Segurado Válido", response.nomeSegurado());

        verify(apoliceRepository, times(1)).save(any(Apolice.class));
    }

    @Test
    @DisplayName("Deve barrar a emissão se a data de início for posterior ao fim de vigência")
    void erroDataVigenciaInvertida() {

        LocalDate inicioInvalido = LocalDate.now().plusYears(10);
        LocalDate fimInvalido = LocalDate.now();

        ApoliceRequestDTO request = new ApoliceRequestDTO(
                "APP-2026-TESTE",
                new BigDecimal("1000.00"),
                new BigDecimal("50000.00"),
                inicioInvalido,
                fimInvalido,
                1L
        );

        Segurado seguradoFake = new Segurado(12L, "Segurado Teste", "11122233344", "seguradoteste@email.com");
        when(seguradoService.buscarPorId(12L)).thenReturn(seguradoFake);
        when(apoliceRepository.findByNumeroApolice("AP-2026-TESTE")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            apoliceService.emitirApolice(request);
        });

        assertEquals("A data início de vigência não pode ser posterior data de término.", exception.getMessage());

        verify(apoliceRepository, never()).save(any(Apolice.class));
    }

    @Test
    @DisplayName("Deve barrar a emissão se a cobertura for menor ou igual ao prêmio cobrado.")
    void erroFinanceiroCoberturaInsuficiente(){

        ApoliceRequestDTO request = new ApoliceRequestDTO(
                "AP-2026-TESTE",
                new BigDecimal("2000.00"),
                new BigDecimal("1500.00"),
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                12L
        );

        Segurado seguradoFake = new Segurado(12L, "Segurado Teste", "11122233344", "seguradoteste@email.com");

        when(seguradoService.buscarPorId(12L)).thenReturn(seguradoFake);
        when(apoliceRepository.findByNumeroApolice("AP-2026-TESTE")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->{
            apoliceService.emitirApolice(request);
        });

        assertEquals("O valor da cobertura deve ser maior do que o valor do prêmio cobrado.", exception.getMessage());
        verify(apoliceRepository, never()).save(any(Apolice.class));
    }
}