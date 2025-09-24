package com.example.clinica.controller;

import com.example.clinica.dto.MedicoCreateDTO;
import com.example.clinica.dto.MedicoResponseDTO;
import com.example.clinica.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService service;

    @Operation(summary = "Cria um médico")
    @ApiResponse(responseCode = "201", description = "Criado")
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody MedicoCreateDTO dto) {
        Long id = service.criar(dto);
        return ResponseEntity.created(URI.create("/medicos/" + id)).build();
    }

    @Operation(summary = "Lista médicos com paginação")
    @GetMapping
    public Page<MedicoResponseDTO> listar(@ParameterObject Pageable pageable) {
        return service.listar(pageable);
    }
}
