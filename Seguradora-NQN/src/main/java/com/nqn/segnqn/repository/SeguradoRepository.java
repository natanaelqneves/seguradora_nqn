package com.nqn.segnqn.repository;

import com.nqn.segnqn.model.Segurado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeguradoRepository extends JpaRepository<Segurado, Long> {

    Optional<Segurado> findByCpf(String cpf);

}
