package com.fluxin.flux_in.validations.WorkingHour;

import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

class ValidationStartAndFinalOfWorkdayTest {

    private final ValidationStartAndFinalOfWorkday validation = new ValidationStartAndFinalOfWorkday();

    @Test
    @DisplayName("Deveria lançar uma ValidationException ao passar horario de inicio do expediente posterior ao horario de encerramento.")
    void validateStartAndFinalOfWorkdayScenario01() {
        WorkingHourDTO workingHourDTO = new WorkingHourDTO(
                LocalTime.of(18, 0),
                LocalTime.of(17, 0),
                LocalTime.of(12, 0),
                60,
                Set.of(1, 2, 3, 4, 5)
        );

        Assertions.assertThrows(ValidationException.class, () -> validation.validate(workingHourDTO));
    }

    @Test
    @DisplayName("Deveria fazer nada ao passar horario de inicio do expediente anterior ao horario de encerramento.")
    void validateStartAndFinalOfWorkdayScenario02() {
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