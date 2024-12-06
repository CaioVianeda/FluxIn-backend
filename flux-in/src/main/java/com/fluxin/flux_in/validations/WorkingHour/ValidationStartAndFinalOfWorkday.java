package com.fluxin.flux_in.validations.WorkingHour;

import com.fluxin.flux_in.dto.WorkingHourDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ValidationStartAndFinalOfWorkday implements ValidationWorkingHour {
    public void validate(WorkingHourDTO dto) {
        if (dto.startOfWorkday().isAfter(dto.endOfWorkday()))
            throw new ValidationException("Horário de inicio do expediente deve ser anterior ao de encerramento.");
    }
}
