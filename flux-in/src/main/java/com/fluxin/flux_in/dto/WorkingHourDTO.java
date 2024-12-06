package com.fluxin.flux_in.dto;

import com.fluxin.flux_in.model.WorkingHour;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.Set;

public record WorkingHourDTO(
        @NotNull(message = "Inicio do expediente não deve ser nulo.")
        LocalTime startOfWorkday,
        @NotNull(message = "Fim do expediente não deve ser nulo.")
        LocalTime endOfWorkday,
        @NotNull(message = "Inicio do intervalo não deve ser nulo.")
        LocalTime startInterval,
        @NotNull(message = "Duração do intervalo não deve ser nulo.")
        Integer intervalDuration,
        @NotNull(message = "Informe os dias de trabalho.")
        Set<Integer> workingDays) {
    public WorkingHourDTO(WorkingHour workingHour) {
        this(workingHour.getStartOfWorkDay(), workingHour.getEndOfWorkDay(), workingHour.getStartInterval(), workingHour.getIntervalDuration(), workingHour.getWorkingDays());
    }
}