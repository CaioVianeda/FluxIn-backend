package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import com.fluxin.flux_in.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class ValidationDateIsWorkingDay implements ValidationScheduling{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void validation(CreateSchedulingDTO schedulingDTO) {
        var employee = employeeRepository.findById(schedulingDTO.employeeID()).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + schedulingDTO.employeeID() + " não encontrado."));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", Locale.forLanguageTag("pt-BR"));
        DayOfWeek dayOfWeek = schedulingDTO.date().getDayOfWeek();
        if(!employee.isWorkingday(dayOfWeek.getValue()))
            throw new ValidationException(schedulingDTO.date().format(formatter) + " faz não parte do expediente do funcionário.");
    }
}
