package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.employeeDTO.CreateEmployeeDTO;
import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
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
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class ValidationStartScheduleIsNotEmployeeIntervalTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private  ValidationStartScheduleIsNotEmployeeInterval validation;
    @Mock
    private CreateSchedulingDTO schedulingDTO;


    @Test
    @DisplayName("Deveria permitir a criação do agendamento.")
    void validationStartScheduleIsNotEmployeeIntervalScenario01() {
        BDDMockito.given(employeeRepository.findById(schedulingDTO.employeeID())).willReturn (Optional.of(createEmployee()));
        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.of(2024, 12, 23, 14, 30));

        Assertions.assertDoesNotThrow(() -> validation.validation(schedulingDTO));
    }

    @Test
    @DisplayName("Não deveria permitir a criação do agendamento.")
    void validationStartScheduleIsNotEmployeeIntervalScenario02() {
        BDDMockito.given(employeeRepository.findById(schedulingDTO.employeeID())).willReturn (Optional.of(createEmployee()));
        BDDMockito.given(schedulingDTO.date()).willReturn(LocalDateTime.of(2024, 12, 23, 12, 30));

        Assertions.assertThrows(ValidationException.class, () -> validation.validation(schedulingDTO));
    }

    private Employee createEmployee() {
        var workingHour = new WorkingHour (new WorkingHourDTO(
                LocalTime.of(8, 0),
                LocalTime.of(17, 0),
                LocalTime.of(12, 0),
                60,
                Set.of(1, 2, 3, 4, 5)
        ));
        Employee employee = new Employee(new CreateEmployeeDTO("Tester", "(11)111111111", "tester@gmail.com", null), null);
        employee.setWorkingHour(workingHour);
        return  employee;
    }

}