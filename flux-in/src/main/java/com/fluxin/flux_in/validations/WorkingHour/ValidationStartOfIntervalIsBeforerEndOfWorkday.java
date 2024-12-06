package com.fluxin.flux_in.validations.WorkingHour;

import com.fluxin.flux_in.dto.WorkingHourDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ValidationStartOfIntervalIsBeforerEndOfWorkday implements ValidationWorkingHour {
    public void validate(WorkingHourDTO dto) {
        if(dto.startInterval().isAfter(dto.endOfWorkday()))
            throw new ValidationException("O inicio do intervalo deve ser anterior ao término do expediente do funcionário.");

    }
}
