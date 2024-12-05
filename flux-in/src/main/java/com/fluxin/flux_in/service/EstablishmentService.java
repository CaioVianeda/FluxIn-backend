package com.fluxin.flux_in.service;

import com.fluxin.flux_in.dto.CreateEstablishmentDTO;
import com.fluxin.flux_in.dto.EstablishmentDTO;
import com.fluxin.flux_in.model.Establishment;
import com.fluxin.flux_in.repository.EstablishmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstablishmentService {

    @Autowired
    EstablishmentRepository establishmentRepository;

    @Transactional
    public EstablishmentDTO createEstablishment(CreateEstablishmentDTO establishmentDTO) {
        Establishment establishment = new Establishment(establishmentDTO);
        establishmentRepository.save(establishment);
        return new EstablishmentDTO(establishment);
    }

    public List<EstablishmentDTO> getEstablishments() {
        return establishmentRepository.findAll().stream().map(EstablishmentDTO::new).collect(Collectors.toList());
    }

    public EstablishmentDTO getEstablishmentById(Long id) {
        var establishment = establishmentRepository.findById(id);
        establishment.orElseThrow(() -> new EntityNotFoundException("Estabelecimento com ID " + id + " não encontrado."));
        return new EstablishmentDTO(establishment.get());
    }

    @Transactional
    public EstablishmentDTO updateEstablishment(Long id, CreateEstablishmentDTO establishmentDTO) {
        var establishment = establishmentRepository.findById(id);
        establishment.orElseThrow(() -> new EntityNotFoundException("Estabelecimento com ID " + id + " não encontrado."));
        establishment.get().setName(establishmentDTO.name());
        establishment.get().setEmail(establishmentDTO.email());
        establishmentRepository.save(establishment.get());
        return new EstablishmentDTO(establishment.get());
    }

    public void deleteEstablishment(Long id) {
        if(establishmentRepository.existsById(id)) {
            establishmentRepository.deleteById(id);
        } else throw new EntityNotFoundException("Estabelecimento com ID " + id + " não encontrado.");
    }
}
