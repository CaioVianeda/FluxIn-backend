package com.fluxin.flux_in.dto.procedureDTO;

import com.fluxin.flux_in.model.Procedure;

import java.math.BigDecimal;

public record ResumeProcedureDTO(Long id, String name, BigDecimal price){
    public ResumeProcedureDTO(Procedure procedure){
        this(procedure.getId(), procedure.getName(), procedure.getPrice());
    }
}
