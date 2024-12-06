package com.fluxin.flux_in.dto.establishmentDTO;

import com.fluxin.flux_in.model.Establishment;

public record EstablishmentDTO(Long id, String name, String email) {
    public EstablishmentDTO(Establishment establishment){
        this(establishment.getId(),establishment.getName(), establishment.getEmail());
    }
}
