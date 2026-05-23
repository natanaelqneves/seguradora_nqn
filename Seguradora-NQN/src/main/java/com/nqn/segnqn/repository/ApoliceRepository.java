package com.nqn.segnqn.repository;

import com.nqn.segnqn.model.Apolice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApoliceRepository extends JpaRepository<Apolice, Long> {

    Optional<Apolice> findByNumeroApolice(String numeroApolice);

}
