package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import com.fluxin.flux_in.model.Employee;
import com.fluxin.flux_in.model.WorkingHour;
import com.fluxin.flux_in.repository.EmployeeRepository;
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
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class ValidationDateIsWorkingDayTest {

    @InjectMocks
    private ValidationDateIsWorkingDay validation;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Employee employee;

    @Mock
    private WorkingHour workingHour;

    @Mock
    private CreateSchedulingDTO schedulingDTO;

    @Test
    @DisplayName("Deveria lançar uma ValidationException se a data solicitada não estiver dentro do expediente semanal do funcionário.")
    void validationValidationDateIsWorkingDayScenario01() {
        BDDMockito.given(employeeRepository.findById(BDDMockito.anyLong())).willReturn(Optional.of(employee));
        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.of(2024, 12, 29, 0, 0));
        Assertions.assertThrows(ValidationException.class, () -> validation.validation(schedulingDTO));
    }

    @Test
    @DisplayName("Deveria fazer nada se a data solicitada estiver dentro do expediente semanal do funcionário.")
    void validationValidationDateIsWorkingDayScenario02() {
        Employee employee1 = new Employee();
        BDDMockito.given(workingHour.getWorkingDays()).willReturn(Set.of(1, 2, 3, 4, 5));
        employee1.setWorkingHour(workingHour);
        BDDMockito.given(employeeRepository.findById(BDDMockito.anyLong())).willReturn(Optional.of(employee1));
        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.of(2024, 12, 30, 0, 0));
        validation.validation(schedulingDTO);
    }
}