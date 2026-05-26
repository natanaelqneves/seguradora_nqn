package com.nqn.segnqn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CorretorRequestDTO(

        @NotBlank(message = "O nome do corretor é obrigatório.")
        @Size(max = 150, message = "O nome não deve exceder 150 caracteres.")
        String nome,

        @NotBlank(message = "O registro na SUSEP é obrigatório.")
        @Size(min = 11, max = 14, message = "O registro deve ter um formato válido.")
        String susep
) {
}
