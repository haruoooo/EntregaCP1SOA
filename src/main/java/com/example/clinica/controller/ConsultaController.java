package com.example.clinica.controller;

import com.example.clinica.dto.ConsultaCreateDTO;
import com.example.clinica.dto.ConsultaResponseDTO;
import com.example.clinica.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService service;

    @Operation(summary = "Agenda uma consulta")
    @ApiResponse(responseCode = "201", description = "Criado")
    @PostMapping
    public Long agendar(@Valid @RequestBody ConsultaCreateDTO dto) {
        return service.agendar(dto);
    }

    @Operation(summary = "Lista consultas (paginado); opcional filtrar por médico")
    @GetMapping
    public Page<ConsultaResponseDTO> listar(
            @RequestParam(name = "medicoId", required = false) Long medicoId,
            @ParameterObject Pageable pageable) {
        if (medicoId != null) return service.listarPorMedico(medicoId, pageable);
        return service.listar(pageable);
    }
}
