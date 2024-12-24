package com.fluxin.flux_in.validations.WorkingHour;

import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

class ValidationEndOfIntervalIsBeforeEndOfWorkdayTest {

    private final ValidationEndOfIntervalIsBeforeEndOfWorkday validation = new ValidationEndOfIntervalIsBeforeEndOfWorkday();

    @Test
    @DisplayName("Deveria lançar ValidationException quando o horário de inicio de intervalo for posterior ao final do expediente.")
    void validationIntervalScenario01() {
        WorkingHourDTO workingHourDTO = new WorkingHourDTO(
                LocalTime.of(9, 30),
                LocalTime.of(14, 0),
                LocalTime.of(13, 30),
                60,
                Set.of(1, 2, 3, 4, 5)
        );
        Assertions.assertThrows(ValidationException.class, () -> validation.validate(workingHourDTO));
    }

    @Test
    @DisplayName("Deveria fazer nada quando o horário de intervalo do expediente for anterior ao final do expediente.")
    void validationIntervalScenario02() {
        WorkingHourDTO workingHourDTO = new WorkingHourDTO(
                LocalTime.of(8, 0),
                LocalTime.of(17, 0),
                LocalTime.of(12, 0),
                60,
                Set.of(1, 2, 3, 4, 5)
        );

        validation.validate(workingHourDTO);
    }
}