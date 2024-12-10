package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidationIsFutureDate implements ValidationScheduling{
    public void validation(CreateSchedulingDTO schedulingDTO) {
        if (schedulingDTO.date().isBefore(LocalDateTime.now())){
            throw new ValidationException("Agendamentos só podem ser realizados para uma data futura.");
        }
    }
}
