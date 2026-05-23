package com.nqn.segnqn.service;

import com.nqn.segnqn.dto.SeguradoRequestDTO;
import com.nqn.segnqn.model.Segurado;
import com.nqn.segnqn.repository.SeguradoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeguradoService {

    private final SeguradoRepository seguradoRepository;

    public SeguradoService(SeguradoRepository seguradoRepository) {
        this.seguradoRepository = seguradoRepository;
    }

    @Transactional
    public Segurado cadastrar(SeguradoRequestDTO dto){
        //NÃO TINHA O ISPRESENT ENTÃO USEI O DIFERENTE DE NULL
        if(seguradoRepository.findByCpf(dto.cpf()).isPresent()){
            throw new IllegalArgumentException("Já existe um segurado cadastrado com este CPF.");
        }

        Segurado segurado = new Segurado();
        segurado.setNome(dto.nome());
        segurado.setCpf(dto.cpf());
        segurado.setEmail(dto.email());

        return seguradoRepository.save(segurado);
    }

    public List<Segurado> listarSegurados(){
        return seguradoRepository.findAll();
    }

    public Segurado buscarPorId(Long id){
        return seguradoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seguro não encontrado."));
    }
}
