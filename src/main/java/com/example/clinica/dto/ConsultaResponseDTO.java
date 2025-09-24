package com.example.clinica.dto;

import com.example.clinica.domain.model.StatusConsulta;
import java.time.LocalDateTime;

public record ConsultaResponseDTO(
        Long id,
        Long pacienteId,
        Long medicoId,
        StatusConsulta status,
        LocalDateTime dataHora
) {}
