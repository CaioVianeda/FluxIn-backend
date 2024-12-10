package com.fluxin.flux_in.service;

import com.fluxin.flux_in.dto.procedureDTO.CreateProcedureDTO;
import com.fluxin.flux_in.dto.procedureDTO.ProcedureDTO;
import com.fluxin.flux_in.model.Procedure;
import com.fluxin.flux_in.repository.EstablishmentRepository;
import com.fluxin.flux_in.repository.ProcedureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProcedureService {
    @Autowired
    private ProcedureRepository procedureRepository;
    @Autowired
    private EstablishmentRepository establishmentRepository;

    public ProcedureDTO createProcedure(Long id, CreateProcedureDTO procedureDTO) {
        var establishment = establishmentRepository.findById(id);
        establishment.orElseThrow(() -> new EntityNotFoundException("Estabelecimento com ID " + id + " não encontrado."));
        Procedure procedure = new Procedure(procedureDTO, establishment.get());
        procedureRepository.save(procedure);
        return new ProcedureDTO(procedure);
    }

    public List<ProcedureDTO> getAllProceduresByEstablishmentID(Long id) {
        var procedures = procedureRepository.findAll();
        return procedures.stream().map(ProcedureDTO::new).collect(Collectors.toList());
    }

    public ProcedureDTO getProcedureByID(Long id) {
        var procedure = procedureRepository.findById(id);
        procedure.orElseThrow(() -> new EntityNotFoundException("Procedimento com ID " + id + " não encontrado."));
        return new ProcedureDTO(procedure.get());
    }

    public ProcedureDTO updateProcedureByID(Long id, CreateProcedureDTO procedureDTO) {
        var procedure = procedureRepository.findById(id);
        procedure.orElseThrow(() -> new EntityNotFoundException("Procedimento com ID " + id + " não encontrado."));
        procedure.get().setName(procedureDTO.name().trim().toLowerCase());
        procedure.get().setPrice(procedureDTO.price());
        procedure.get().setDuration(procedureDTO.duration());
        return new ProcedureDTO(procedure.get());
    }


    public void deleteProcedureByID(Long id) {
        var procedure = procedureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Procedimento com ID " + id + " não encontrado."));
        procedure.getSchedulings().forEach(scheduling -> scheduling.getProcedures().removeIf(p -> p.getId().equals(id)));
        procedureRepository.deleteById(id);
    }
}
