package com.nqn.segnqn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CorretorRequestDTO(

        @NotBlank(message = "O nome do corretor é obrigatório.")
        @Size(max = 150, message = "O nome não deve exceder 150 caracteres.")
        String nome,

        @NotBlank(message = "O registro na SUSEP é obrigatório.")
        @Size(min = 11, max = 14, message = "O registro deve ter um formato válido.")
        String susep,

        @NotBlank(message = "O Login é obrigatório.")
        @Size(max = 16, message = "O login deve ter um formato válido.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 10, message = "A senha deve ter um formato válido.")
        String senha
) {
}
