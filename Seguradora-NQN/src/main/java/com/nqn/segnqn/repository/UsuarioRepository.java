package com.nqn.segnqn.repository;

import com.nqn.segnqn.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNomeDeUsuario(String nomeDeUsuario);
}
