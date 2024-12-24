package com.fluxin.flux_in.service;

import com.fluxin.flux_in.dto.establishmentDTO.CreateEstablishmentDTO;
import com.fluxin.flux_in.model.Establishment;
import com.fluxin.flux_in.repository.EstablishmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class EstablishmentServiceTest {

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Test
    void createEstablishment() {
        Establishment establishment = new Establishment(establishmentDTO());
    }

    private CreateEstablishmentDTO establishmentDTO() {
        return  new CreateEstablishmentDTO("establishment test", "test@fluxin.com");
    }
}