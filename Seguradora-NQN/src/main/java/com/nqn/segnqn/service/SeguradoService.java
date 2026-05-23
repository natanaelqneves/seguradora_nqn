package com.nqn.segnqn.service;

import com.nqn.segnqn.dto.SeguradoRequestDTO;
import com.nqn.segnqn.dto.SeguradoResponseDTO;
import com.nqn.segnqn.model.Segurado;
import com.nqn.segnqn.repository.SeguradoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeguradoService {

    private final SeguradoRepository seguradoRepository;

    public SeguradoService(SeguradoRepository seguradoRepository) {
        this.seguradoRepository = seguradoRepository;
    }

    @Transactional
    public SeguradoResponseDTO cadastrar(SeguradoRequestDTO dto){

        if(seguradoRepository.findByCpf(dto.cpf()).isPresent()){
            throw new IllegalArgumentException("Já existe um segurado cadastrado com este CPF.");
        }

        Segurado segurado = new Segurado();
        segurado.setNome(dto.nome());
        segurado.setCpf(dto.cpf());
        segurado.setEmail(dto.email());

        Segurado salvo = seguradoRepository.save(segurado);

        return new SeguradoResponseDTO(salvo.getNome(), salvo.getCpf(), salvo.getEmail());
    }

    public List<SeguradoResponseDTO> listarSegurados(){
        List<SeguradoResponseDTO> lista = new ArrayList<>();
        seguradoRepository.findAll().forEach(seg -> lista.add(new SeguradoResponseDTO(seg.getNome(), seg.getCpf(), seg.getEmail())));

        return lista;
    }

    public Segurado buscarPorId(Long id){
        return seguradoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seguro não encontrado."));
    }

    @Transactional
    public SeguradoResponseDTO atualizar(Long id, SeguradoRequestDTO dto){
        Segurado seguradoExistente = buscarPorId(id);

        if(!seguradoExistente.getCpf().equals(dto.cpf()) && seguradoRepository.findByCpf(dto.cpf()).isPresent()) {
    throw new IllegalArgumentException("O novo CPF já está sendo utilizado.");
        }

        seguradoExistente.setNome(dto.nome());
        seguradoExistente.setCpf(dto.cpf());
        seguradoExistente.setEmail(dto.email());
        seguradoRepository.save(seguradoExistente);

        return new SeguradoResponseDTO(seguradoExistente.getNome(), seguradoExistente.getCpf(), seguradoExistente.getEmail());
    }

    @Transactional
    public void deletar(Long id){
        Segurado segurado = buscarPorId(id);
        seguradoRepository.delete(segurado);
    }
}
