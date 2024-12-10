package com.fluxin.flux_in.dto.schedulingDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreateSchedulingDTO(
        @NotNull(message = "O ID do estabelecimento deve ser informado.")
        Long establishmentID,
        @NotNull(message = "O ID do cliente deve ser informado.")
        Long clientID,
        @NotNull(message = "O ID do funcionário deve ser informado.")
        Long employeeID,
        @NotEmpty(message = "É necessário informar o ID de ao menos um procedimento.")
        List<Long> proceduresIDs,
        @NotNull(message = "Informe a data do agendamento.")
        LocalDateTime date) {
}
