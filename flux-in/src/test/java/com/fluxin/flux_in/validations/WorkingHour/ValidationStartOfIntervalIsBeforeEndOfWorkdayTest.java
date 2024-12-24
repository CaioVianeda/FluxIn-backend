package com.fluxin.flux_in.validations.WorkingHour;

import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

class ValidationStartOfIntervalIsBeforeEndOfWorkdayTest {

    private final ValidationStartOfIntervalIsBeforeEndOfWorkday validation = new ValidationStartOfIntervalIsBeforeEndOfWorkday();

    @Test
    @DisplayName("Deveria lançar exception ao passar horario de inicio do intervalo posterior ao horario de encerramento.")
    void validationStartOfIntervalIsBeforeEndOfWorkdayScenario01() {
        WorkingHourDTO workingHourDTO = new WorkingHourDTO(
                LocalTime.of(10, 0),
                LocalTime.of(17, 0),
                LocalTime.of(18, 0),
                60,
                Set.of(1, 2, 3, 4, 5)
        );
        Assertions.assertThrows(ValidationException.class, () -> validation.validate(workingHourDTO));
    }

    @Test
    @DisplayName("Deveria fazer nada ao passar horario de inicio do intervalo anterior ao horario de encerramento.")
    void validationStartOfIntervalIsBeforeEndOfWorkdayScenario02() {
        WorkingHourDTO workingHourDTO = new WorkingHourDTO(
                LocalTime.of(10, 0),
                LocalTime.of(17, 0),
                LocalTime.of(12, 0),
                60,
                Set.of(1, 2, 3, 4, 5)
        );
        validation.validate(workingHourDTO);
    }

}