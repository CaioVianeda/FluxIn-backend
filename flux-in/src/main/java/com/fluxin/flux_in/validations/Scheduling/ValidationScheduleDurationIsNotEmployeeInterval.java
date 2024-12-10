package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import com.fluxin.flux_in.model.Procedure;
import com.fluxin.flux_in.repository.EmployeeRepository;
import com.fluxin.flux_in.repository.ProcedureRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationScheduleDurationIsNotEmployeeInterval implements ValidationScheduling{

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProcedureRepository procedureRepository;

    public void validation(CreateSchedulingDTO schedulingDTO) {
        var employee = employeeRepository.findById(schedulingDTO.employeeID()).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + schedulingDTO.employeeID() + " não encontrado."));
        var procedures = procedureRepository.findByIdIn(schedulingDTO.proceduresIDs());
        var duration = procedures.stream().mapToInt(Procedure::getDuration).sum();
        var startScheduling = schedulingDTO.date().toLocalTime();
        var endScheduling = startScheduling.plusMinutes(duration);
        if(startScheduling.isBefore(employee.getWorkingHour().getStartInterval()) && !endScheduling.isBefore(employee.getWorkingHour().getStartInterval())) {
            throw new ValidationException("Não foi possível realizar o atendimento pois a previsão de término está em conflito com o intervalo do funcionário.");
        }
    }
}
