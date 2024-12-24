package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class ValidationIsFutureDateTest {

    @InjectMocks
    private ValidationIsFutureDate validation;

    @Mock
    private CreateSchedulingDTO schedulingDTO;

    @Test
    @DisplayName("Deveria lançar exception ao passar uma data passada.")
    void validationIsFutureDateTestScenario01() {
        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.now().plusMinutes(-1));
        Assertions.assertThrows(ValidationException.class, () -> validation.validation(schedulingDTO));
    }

    @Test
    @DisplayName("Deveria fazer nada ao passar uma data atual.")
    void validationIsFutureDateTestScenario02() {
        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.now().plusMinutes(1));
        validation.validation(schedulingDTO);
    }

}