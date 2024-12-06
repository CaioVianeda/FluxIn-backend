package com.fluxin.flux_in.validations.WorkingHour;

import com.fluxin.flux_in.dto.WorkingHourDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ValidationStartOfIntervalIsAfterStartOfWorkday implements ValidationWorkingHour {
    public void validate(WorkingHourDTO dto) {
        if(dto.startInterval().isBefore(dto.startOfWorkday()))
            throw new ValidationException("O inicio do intervalo deve ser após o inicio do expediente do funcionário.");

    }
}
