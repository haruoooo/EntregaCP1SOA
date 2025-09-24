package com.example.clinica.service;

import com.example.clinica.domain.model.Consulta;
import com.example.clinica.domain.model.Medico;
import com.example.clinica.domain.model.Paciente;
import com.example.clinica.domain.model.StatusConsulta;
import com.example.clinica.dto.ConsultaCreateDTO;
import com.example.clinica.dto.ConsultaResponseDTO;
import com.example.clinica.repository.ConsultaRepository;
import com.example.clinica.repository.MedicoRepository;
import com.example.clinica.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepo;
    private final PacienteRepository pacienteRepo;
    private final MedicoRepository medicoRepo;

    @Transactional
    public Long agendar(ConsultaCreateDTO dto) {
        Paciente paciente = pacienteRepo.findById(dto.pacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        Medico medico = medicoRepo.findById(dto.medicoId())
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));

        // Melhoria escolhida para implementação: evitar overbooking de consultas para o mesmo médico
        if (consultaRepo.existsByMedicoIdAndDataHora(medico.getId(), dto.dataHora())) {
            throw new IllegalArgumentException("Médico já possui consulta neste horário");
        }

        Consulta c = Consulta.builder()
                .paciente(paciente)
                .medico(medico)
                .status(StatusConsulta.AGENDADA)
                .dataHora(dto.dataHora())
                .build();

        return consultaRepo.save(c).getId();
    }

    @Transactional(readOnly = true)
    public Page<ConsultaResponseDTO> listar(Pageable pageable) {
        return consultaRepo.findAll(pageable).map(c ->
                new ConsultaResponseDTO(
                        c.getId(),
                        c.getPaciente().getId(),
                        c.getMedico().getId(),
                        c.getStatus(),
                        c.getDataHora()
                )
        );
    }

    @Transactional(readOnly = true)
    public Page<ConsultaResponseDTO> listarPorMedico(Long medicoId, Pageable pageable) {
        return consultaRepo.findByMedicoId(medicoId, pageable).map(c ->
                new ConsultaResponseDTO(
                        c.getId(),
                        c.getPaciente().getId(),
                        c.getMedico().getId(),
                        c.getStatus(),
                        c.getDataHora()
                )
        );
    }
}
