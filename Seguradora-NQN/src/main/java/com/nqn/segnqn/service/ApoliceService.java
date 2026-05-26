package com.nqn.segnqn.service;

import com.nqn.segnqn.dto.ApoliceRequestDTO;
import com.nqn.segnqn.dto.ApoliceResponseDTO;
import com.nqn.segnqn.model.Apolice;
import com.nqn.segnqn.model.Corretor;
import com.nqn.segnqn.model.Segurado;
import com.nqn.segnqn.repository.ApoliceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApoliceService {

    private final ApoliceRepository apoliceRepository;
    private final SeguradoService seguradoService;
    private final CorretorService corretorService;

    public ApoliceService(ApoliceRepository apoliceRepository, SeguradoService seguradoService, CorretorService corretorService) {
        this.apoliceRepository = apoliceRepository;
        this.seguradoService = seguradoService;
        this.corretorService = corretorService;
    }

    public ApoliceResponseDTO emitirApolice(ApoliceRequestDTO dto){

        if(dto.inicioVigencia().isAfter(dto.fimVigencia())){
            throw  new IllegalArgumentException("A data início de vigência não pode ser posterior data de término.");
        }

        if(dto.valorCobertura().compareTo(dto.valorPremio()) <= 0) {
            throw  new IllegalArgumentException("O valor da cobertura deve ser maior do que o valor do prêmio cobrado.");
        }

        if(apoliceRepository.findByNumeroApolice(dto.numeroApolice()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma apólice emitida com este número.");
        }

        Segurado segurado = seguradoService.buscarPorId(dto.seguradoId());
        Corretor corretor = corretorService.buscarPorId(dto.corretorId());

        Apolice apolice = new Apolice(
                null,
                dto.numeroApolice(),
                dto.valorPremio(),
                dto.valorCobertura(),
                dto.inicioVigencia(),
                dto.fimVigencia(),
                segurado,
                corretor
        );

        Apolice salva = apoliceRepository.save(apolice);

        return new ApoliceResponseDTO(salva.getNumeroApolice(),
                salva.getValorPremio(),
                salva.getValorCobertura(),
                salva.getInicioVigencia(),
                salva.getFimVigencia(),
                salva.getSegurado().getNome(),
                salva.getSegurado().getCpf(),
                salva.getCorretor().getNome(),
                salva.getCorretor().getSusep()
        );
    }

    public List<ApoliceResponseDTO> listarApolices(){
        List<ApoliceResponseDTO> lista = new ArrayList<>();

        apoliceRepository.findAll().forEach(ap -> lista.add(new ApoliceResponseDTO(ap.getNumeroApolice(),
                ap.getValorPremio(),
                ap.getValorCobertura(),
                ap.getInicioVigencia(),
                ap.getFimVigencia(),
                ap.getSegurado().getNome(),
                ap.getSegurado().getCpf(),
                ap.getCorretor().getNome(),
                ap.getCorretor().getSusep()
        )));

        return lista;
    }

}
