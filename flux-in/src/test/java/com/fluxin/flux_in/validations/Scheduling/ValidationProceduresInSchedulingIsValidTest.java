package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class ValidationProceduresInSchedulingIsValidTest {

    @InjectMocks
    private ValidationProceduresInSchedulingIsValid validation;

    @Mock
    private CreateSchedulingDTO schedulingDTO;

    @Test
    @DisplayName("Deveria lançar exception ao passar um id de procedimento inexistente.")
    void validationProceduresInSchedulingIsValidScenario01() {
        BDDMockito.given(schedulingDTO.proceduresIDs()).willReturn(Arrays.asList(1L, 2L, 3L));
        Assertions.assertThrows(EntityNotFoundException.class, () -> validation.validation(schedulingDTO));
    }

    @Test
    @DisplayName("Deveria fazer nada ao passar ids existentes.")
    void validationProceduresInSchedulingIsValidScenario02() {
        validation.validation(schedulingDTO);
    }
}