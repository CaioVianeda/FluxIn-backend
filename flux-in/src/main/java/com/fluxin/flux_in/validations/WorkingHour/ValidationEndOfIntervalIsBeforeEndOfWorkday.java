package com.fluxin.flux_in.validations.WorkingHour;

import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class ValidationEndOfIntervalIsBeforeEndOfWorkday implements ValidationWorkingHour {
    public void validate(WorkingHourDTO dto) {
        LocalTime endInterval = dto.startInterval().plusMinutes(dto.intervalDuration());
        if(endInterval.isAfter(dto.endOfWorkday()))
            throw new ValidationException("O fim do intervalo deve ser anterior ao término do expediente do funcionário.");

    }
}
