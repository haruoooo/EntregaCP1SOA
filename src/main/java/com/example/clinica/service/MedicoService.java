package com.example.clinica.service;

import com.example.clinica.domain.model.Medico;
import com.example.clinica.dto.MedicoCreateDTO;
import com.example.clinica.dto.MedicoResponseDTO;
import com.example.clinica.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository repo;

    @Transactional
    public Long criar(MedicoCreateDTO dto) {
        repo.findByCrm(dto.crm()).ifPresent(m -> {
            throw new IllegalArgumentException("CRM já cadastrado: " + dto.crm());
        });

        Medico medico = Medico.builder()
                .nome(dto.nome())
                .crm(dto.crm())
                .build();

        return repo.save(medico).getId();
    }

    @Transactional(readOnly = true)
    public Page<MedicoResponseDTO> listar(Pageable pageable) {
        return repo.findAll(pageable)
                .map(m -> new MedicoResponseDTO(m.getId(), m.getNome(), m.getCrm()));
    }
}
