package com.nqn.segnqn.repository;

import com.nqn.segnqn.model.Corretor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorretorRepository extends JpaRepository<Corretor, Long> {

    Optional<Corretor> findBySusep(String susep);

    Optional<Corretor> findByLogin(String login);
}
