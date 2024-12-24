package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import com.fluxin.flux_in.model.Employee;
import com.fluxin.flux_in.model.Procedure;
import com.fluxin.flux_in.model.WorkingHour;
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
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ValidationScheduleDurationIsNotEmployeeIntervalTest {

    @InjectMocks
    private ValidationScheduleDurationIsNotEmployeeInterval validation;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Employee employee;

    @Mock
    private WorkingHour workingHour;

    @Mock
    private CreateSchedulingDTO schedulingDTO;

    @Mock
    private ProcedureRepository procedureRepository;

    @Mock
    private Procedure procedure;

    @Test
    @DisplayName("Deveria lançar ValidationException quando o agendar e o término conflitar com o intervalo do funcionário.")
    void validationScheduleDurationIsNotEmployeeIntervalScenario01() {

        BDDMockito.given(workingHour.getStartInterval()).willReturn(LocalTime.of(12, 0));
        BDDMockito.given(employee.getWorkingHour()).willReturn(workingHour);

        BDDMockito.given(employeeRepository.findById(BDDMockito.anyLong())).willReturn(Optional.of(employee));

        BDDMockito.given(procedure.getDuration()).willReturn(60);

        BDDMockito.given((procedureRepository.findByIdIn(BDDMockito.anyList()))).willReturn(List.of(procedure));

        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.of(2024, 10, 24, 11, 40));

        Assertions.assertThrows(ValidationException.class, () -> validation.validation(schedulingDTO));

    }

    @Test
    @DisplayName("Deveria fazer nada ao agendamento não conflitar com o intervalo do funcionario.")
    void validationScheduleDurationIsNotEmployeeIntervalScenario02() {

        BDDMockito.given(workingHour.getStartInterval()).willReturn(LocalTime.of(12, 0));
        BDDMockito.given(employee.getWorkingHour()).willReturn(workingHour);

        BDDMockito.given(employeeRepository.findById(BDDMockito.anyLong())).willReturn(Optional.of(employee));

        BDDMockito.given(procedure.getDuration()).willReturn(60);

        BDDMockito.given((procedureRepository.findByIdIn(schedulingDTO.proceduresIDs()))).willReturn(List.of(procedure));

        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.of(2024, 10, 24, 10, 40));

        validation.validation(schedulingDTO);

    }

}