package com.nqn.segnqn.repository;

import com.nqn.segnqn.model.Segurado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguradoRepository extends JpaRepository<Segurado, Long> {
}
