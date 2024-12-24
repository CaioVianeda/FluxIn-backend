package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import com.fluxin.flux_in.model.Procedure;
import com.fluxin.flux_in.repository.ProcedureRepository;
import com.fluxin.flux_in.repository.SchedulingRepository;
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
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class ValidationDateAndTimeDontHasSchedulingTest {

    @InjectMocks
    private ValidationDateAndTimeDontHasScheduling validation;

    @Mock
    private CreateSchedulingDTO schedulingDTO;

    @Mock
    private ProcedureRepository procedureRepository;

    @Mock
    private Procedure procedure;

    @Mock
    private SchedulingRepository schedulingRepository;

    @Test
    @DisplayName("Deveria lançar uma validationexception ao possuir agendamento no horario solicitado.")
    void validationDateAndTimeDontHasSchedulingScenario01() {
        BDDMockito.given(procedureRepository.findByIdIn(BDDMockito.anyList())).willReturn(Arrays.asList(procedure, procedure));

        BDDMockito.given(procedure.getDuration()).willReturn(30);

        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.now());

        BDDMockito.given(schedulingRepository.existsSchedulingToEmployeeInInterval(BDDMockito.anyLong(), BDDMockito.any(), BDDMockito.any())).willReturn(true);

        Assertions.assertThrows(ValidationException.class, () -> validation.validation(schedulingDTO));
    }

    @Test
    @DisplayName("Deveria fazer nada ao estar disponivel o horario .")
    void validationDateAndTimeDontHasSchedulingScenario02() {
        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.now());

        BDDMockito.given(schedulingRepository.existsSchedulingToEmployeeInInterval(BDDMockito.anyLong(), BDDMockito.any(), BDDMockito.any())).willReturn(false);
        validation.validation(schedulingDTO);
    }
}