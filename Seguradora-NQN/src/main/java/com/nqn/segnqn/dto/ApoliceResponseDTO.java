package com.nqn.segnqn.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ApoliceResponseDTO(String numeroApolice,
                                 BigDecimal valorPremio,
                                 BigDecimal valorCobertura,
                                 LocalDate inicioVigencia,
                                 LocalDate fimVigencia,
                                 String nomeSegurado,
                                 String cpfSegurado
) {
}