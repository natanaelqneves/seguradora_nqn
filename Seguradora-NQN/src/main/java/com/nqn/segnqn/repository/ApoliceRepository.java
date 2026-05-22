package com.nqn.segnqn.repository;

import com.nqn.segnqn.model.Apolice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApoliceRepository extends JpaRepository<Apolice, Long> {

    Apolice findNumeroApolice(String numeroApolice);

}
