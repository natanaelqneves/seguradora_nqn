package com.nqn.segnqn.dto;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDTO(
        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}
