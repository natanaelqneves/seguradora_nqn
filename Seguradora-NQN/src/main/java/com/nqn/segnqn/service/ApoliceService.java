package com.nqn.segnqn.service;

import com.nqn.segnqn.dto.ApoliceRequestDTO;
import com.nqn.segnqn.model.Apolice;
import com.nqn.segnqn.model.Segurado;
import com.nqn.segnqn.repository.ApoliceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApoliceService {

    private final ApoliceRepository apoliceRepository;
    private final SeguradoService seguradoService;

    public ApoliceService(ApoliceRepository apoliceRepository, SeguradoService seguradoService) {
        this.apoliceRepository = apoliceRepository;
        this.seguradoService = seguradoService;
    }

    public Apolice emitirApolice(ApoliceRequestDTO dto){

        if(dto.inicioVigencia().isAfter(dto.FimVigencia())){
            throw  new IllegalArgumentException("A data início de vigência não pode ser posterior data de término.");
        }

        if(dto.valorCobertura().compareTo(dto.valorPremio()) <= 0) {
            throw  new IllegalArgumentException("O valor da cobertura deve ser maior do que o valor do prêmio cobrado.");
        }

        if(apoliceRepository.findNumeroApolice(dto.numeroApolice()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma apólice emitida com este número.");
        }

        Segurado segurado = seguradoService.buscarPorId(dto.seguradoId());

        Apolice apolice = new Apolice(
                null,
                dto.numeroApolice(),
                dto.valorPremio(),
                dto.valorCobertura(),
                dto.inicioVigencia(),
                dto.FimVigencia(),
                segurado
        );

        return apoliceRepository.save(apolice);
    }

    public List<Apolice> listarApolices(){
        return apoliceRepository.findAll();
    }

}
