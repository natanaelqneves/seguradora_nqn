package com.nqn.segnqn.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SeguradoRequestDTO(

        @NotBlank(message = "O nome do segurado é obrigatório.")
        @Size(max = 150, message = "O nome não deve exceder 150 caracteres.")
        String nome,

        @NotBlank(message = "O CPF é obrigatório.")
        @Size(min = 11, max = 14, message = "O CPF deve ter um formato válido.")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail informado deve ser válido.")
        @Size(max = 100, message = "O e-mail não pode exceder 100 caracteres.")
        String email
) {
}
