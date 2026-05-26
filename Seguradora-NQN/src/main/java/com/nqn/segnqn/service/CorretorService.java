package com.nqn.segnqn.service;

import com.nqn.segnqn.dto.CorretorRequestDTO;
import com.nqn.segnqn.dto.CorretorResponseDTO;
import com.nqn.segnqn.model.Corretor;
import com.nqn.segnqn.model.Usuario;
import com.nqn.segnqn.repository.CorretorRepository;
import com.nqn.segnqn.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CorretorService {

    private final CorretorRepository corretorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CorretorService(CorretorRepository corretorRepository, UsuarioRepository usuarioRepository,PasswordEncoder passwordEncoder) {
        this.corretorRepository = corretorRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CorretorResponseDTO cadastrar(CorretorRequestDTO dto){

        if(corretorRepository.findBySusep(dto.susep()).isPresent()){
            throw new IllegalArgumentException("Já existe um corretor cadastrado com este registro.");
        }

        if(usuarioRepository.findByNomeDeUsuario(dto.usuario()).isPresent()){
            throw new IllegalArgumentException("Já existe um corretor com este registro.");
        }

        String senhaCriptografada = passwordEncoder.encode(dto.senha());

        Usuario usuario = new Usuario();
        usuario.setNomeDeUsuario(dto.usuario());
        usuario.setSenha(senhaCriptografada);

        System.out.println(usuario);

        Corretor corretor = new Corretor();
        corretor.setNome(dto.nome());
        corretor.setSusep(dto.susep());

        System.out.println(corretor);

        usuario.setCorretor(corretor);

        usuarioRepository.save(usuario);
        Corretor salvo = corretorRepository.save(corretor);

        return new CorretorResponseDTO(salvo.getNome(), salvo.getSusep());
    }

    public List<CorretorResponseDTO> listarCorretores(){
        List<CorretorResponseDTO> lista = new ArrayList<>();
        corretorRepository.findAll().forEach(corretor -> lista.add(new CorretorResponseDTO(corretor.getNome(), corretor.getSusep())));

        return lista;
    }

    public CorretorResponseDTO buscarPorSusep(String susep){
        Corretor corretor = corretorRepository.findBySusep(susep).get();

        return new CorretorResponseDTO(corretor.getNome(), corretor.getSusep());
    }

    public Corretor buscarPorId(Long id){
        return corretorRepository.findById(id).get();
    }


    @Transactional
    public CorretorResponseDTO atualizar(Long id, CorretorRequestDTO dto){
        Corretor corretorExistente = buscarPorId(id);

        if(!corretorExistente.getSusep().equals(dto.susep()) && corretorRepository.findBySusep(dto.susep()).isPresent()) {
            throw new IllegalArgumentException("O registro SUSEP já está sendo utilizado.");
        }

        corretorExistente.setNome(dto.nome());
        corretorExistente.setSusep(dto.susep());
        corretorRepository.save(corretorExistente);

        return new CorretorResponseDTO(corretorExistente.getNome(), corretorExistente.getSusep());
    }

    @Transactional
    public void deletar(Long id){
        Corretor corretor = buscarPorId(id);
        corretorRepository.delete(corretor);
    }
}
