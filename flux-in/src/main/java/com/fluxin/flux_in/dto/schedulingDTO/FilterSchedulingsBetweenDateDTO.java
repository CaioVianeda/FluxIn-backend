package com.fluxin.flux_in.dto.schedulingDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FilterSchedulingsBetweenDateDTO(
        @NotNull(message = "Deve ser informado o ID do funcionário.")
        Long employeeID,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @NotNull(message = "Necessário informar a data inicial a ser filtrado os agendamentos.")
        LocalDateTime fromDate,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @NotNull(message = "Necessário informar a data final a ser filtrado os agendamentos.")
        LocalDateTime toDate) {
}
