package com.fluxin.flux_in.validations.WorkingHour;

import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ValidationStartOfIntervalIsBeforerEndOfWorkdayTest {
    @Test
    @DisplayName("Deveria permitir o horário de intervalo do expediente.")
    void validationIntervalScenario01() {
        WorkingHourDTO workingHourDTO = new WorkingHourDTO(
                LocalTime.of(8, 0),
                LocalTime.of(17, 0),
                LocalTime.of(12, 0),
                60,
                Set.of(1, 2, 3, 4, 5)
        );

        ValidationStartOfIntervalIsBeforerEndOfWorkday validation = new ValidationStartOfIntervalIsBeforerEndOfWorkday();

        Assertions.assertDoesNotThrow(() -> validation.validate(workingHourDTO));
    }
}