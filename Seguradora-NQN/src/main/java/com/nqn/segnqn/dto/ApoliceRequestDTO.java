package com.nqn.segnqn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ApoliceRequestDTO(

        @NotBlank(message = "O número da apólice é obrigatório.")
        @Size(max = 20, message = "O número da apólice não pode exceder 20 caracteres.")
        String numeroApolice,

        @NotNull(message = "O valor do prêmio é obrigatório.")
        @Positive(message = "O valor do prêmio deve ser maior que zero.")
        BigDecimal valorPremio,

        @NotNull(message = "O valor da cobertura é obrigatório.")
        @Positive(message = "O valor da cobertura deve ser maior que zero.")
        BigDecimal valorCobertura,

        @NotNull(message = "A data de início de vigência é obrigatória.")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate inicioVigencia,

        @NotNull(message = "A data de fim de vigência é obrigatória.")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate fimVigencia,

        @NotNull(message = "O ID do segurado é obrigatório.")
        Long seguradoId
) {
}
