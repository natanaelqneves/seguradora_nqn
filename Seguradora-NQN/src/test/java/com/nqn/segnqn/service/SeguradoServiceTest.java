package com.nqn.segnqn.service;

import com.nqn.segnqn.dto.SeguradoRequestDTO;
import com.nqn.segnqn.dto.SeguradoResponseDTO;
import com.nqn.segnqn.model.Segurado;
import com.nqn.segnqn.repository.SeguradoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeguradoServiceTest {

    private SeguradoService seguradoService;
    private SeguradoRepository seguradoRepository;

    @BeforeEach
    void setUp() {
        this.seguradoRepository = mock();
        this.seguradoService = new SeguradoService(seguradoRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar segurado com CPF duplicado.")
    void cadastrarComCpfDuplicado(){
        String cpfExistente = "11122233344";

        SeguradoRequestDTO novoSegurado = new SeguradoRequestDTO("Primeiro Segurado", "11122233344", "primeirosegurado@email.com");

        Segurado seguradoCadastrado = new Segurado(3L, "Segurado", cpfExistente, "segurado@email.com");

        when(seguradoRepository.findByCpf(cpfExistente)).thenReturn(Optional.of(seguradoCadastrado));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            seguradoService.cadastrar(novoSegurado);
        });

        assertEquals("Já existe um segurado cadastrado com este CPF.", exception.getMessage());

        verify(seguradoRepository, never()).save(any(Segurado.class));
    }

    @Test
    @DisplayName("Deve cadastrar um segurado com sucesso quando o CPF for inédito")
    void cadastrarComSucesso() {

        SeguradoRequestDTO request = new SeguradoRequestDTO("Segundo Segurado", "00022233344", "segundosegurado@email.com");

        Segurado seguradoSalvo = new Segurado(5L, "Segundo Segurado", "00022233344", "segundosegurado@email.com");

        when(seguradoRepository.findByCpf(request.cpf())).thenReturn(Optional.empty());

        when(seguradoRepository.save(any(Segurado.class))).thenReturn(seguradoSalvo);

        SeguradoResponseDTO response = seguradoService.cadastrar(request);

        assertNotNull(response);
        assertEquals("Segundo Segurado", response.nome());
        assertEquals("00022233344", response.cpf());


        verify(seguradoRepository, times(1)).save(any(Segurado.class));
    }
}