package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import com.fluxin.flux_in.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationStartScheduleIsNotEmployeeInterval implements ValidationScheduling{

    @Autowired
    private EmployeeRepository employeeRepository;

    public void validation(CreateSchedulingDTO schedulingDTO) {
        var employee = employeeRepository.findById(schedulingDTO.employeeID()).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + schedulingDTO.employeeID() + " não encontrado."));
        var time = schedulingDTO.date().toLocalTime();
        if(employee.isInterval(time)) {
            throw new ValidationException("Não é possível realizar atendimento no horário de intervalo do funcionário.");
        }
    }
}
