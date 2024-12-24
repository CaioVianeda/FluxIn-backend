package com.fluxin.flux_in.validations.WorkingHour;

import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ValidationStartOfIntervalIsBeforeEndOfWorkday implements ValidationWorkingHour {
    public void validate(WorkingHourDTO dto) {
        if(dto.startInterval().isAfter(dto.endOfWorkday()))
            throw new ValidationException("O inicio do intervalo deve ser anterior ao término do expediente do funcionário.");
    }
}
