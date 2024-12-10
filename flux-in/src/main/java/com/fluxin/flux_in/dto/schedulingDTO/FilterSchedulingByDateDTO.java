package com.fluxin.flux_in.dto.schedulingDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FilterSchedulingByDateDTO(
        @NotNull(message = "Deve ser informado o ID do funcionário.")
        Long employeeID,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @NotNull(message = "Necessário informar a data a ser filtrado os agendamentos.")
        LocalDateTime date) {
}
