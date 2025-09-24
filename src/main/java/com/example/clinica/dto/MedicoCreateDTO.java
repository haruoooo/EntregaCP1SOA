package com.example.clinica.dto;

import jakarta.validation.constraints.NotBlank;

public record MedicoCreateDTO(
        @NotBlank String nome,
        @NotBlank String crm
) {}
