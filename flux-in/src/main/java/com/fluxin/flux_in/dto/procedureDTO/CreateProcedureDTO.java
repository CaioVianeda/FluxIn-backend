package com.fluxin.flux_in.dto.procedureDTO;

import com.fluxin.flux_in.model.Procedure;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProcedureDTO(@NotBlank(message = "Preencha o nome do procedimento.") String name,
                                 @NotNull(message = "Preencha o valor do procedimento") BigDecimal price,
                                 @NotNull (message = "Preencha a duração do procedimento.")
                                 @Min(1)
        Integer duration){
}
