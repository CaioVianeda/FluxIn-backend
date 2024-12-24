package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import com.fluxin.flux_in.model.Employee;
import com.fluxin.flux_in.model.Procedure;
import com.fluxin.flux_in.repository.EmployeeRepository;
import com.fluxin.flux_in.repository.ProcedureRepository;
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
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ValidationEndOfScheduleIsNotIntervalTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProcedureRepository procedureRepository;

    @InjectMocks
    private ValidationEndOfScheduleIsNotInterval validation;

    @Mock
    private CreateSchedulingDTO schedulingDTO;

    @Mock
    private Employee employee;

    @Mock
    private Procedure procedure;

    @Test
    @DisplayName("Deveria lançar uma ValidationException ao passar um agendamento com término durante o intervalo do funcionário.")
    void validationEndOfScheduleIsNotIntervalScenario01() {
        BDDMockito.given(employeeRepository.findById(BDDMockito.anyLong())).willReturn(Optional.of(employee));
        BDDMockito.given(procedureRepository.findByIdIn(schedulingDTO.proceduresIDs())).willReturn(List.of(procedure));
        BDDMockito.given(procedure.getDuration()).willReturn(60);
        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.of(2024, 12, 23, 12, 0));
        BDDMockito.given(employee.isInterval(BDDMockito.any())).willReturn(true);

        Assertions.assertThrows(ValidationException.class, () -> validation.validation(schedulingDTO));
    }

    @Test
    @DisplayName("Deveria fazer nada ao passar um agendamento em que o término não conflite com o intervalo do funcionário.")
    void validationEndOfScheduleIsNotIntervalScenario02() {
        BDDMockito.given(employeeRepository.findById(BDDMockito.anyLong())).willReturn(Optional.of(employee));
        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.now());
        BDDMockito.given(employee.isInterval(BDDMockito.any())).willReturn(false);
        validation.validation(schedulingDTO);
    }

}