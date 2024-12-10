package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import com.fluxin.flux_in.repository.ProcedureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationProceduresInSchedulingIsValid implements ValidationScheduling{
    @Autowired
    ProcedureRepository procedureRepository;

    public void validation(CreateSchedulingDTO schedulingDTO) {
        schedulingDTO.proceduresIDs().forEach(id -> {
            if(!procedureRepository.existsById(id))
                throw new EntityNotFoundException("Procedimento com ID " + id + " não encontrado.");
        });
    }
}
