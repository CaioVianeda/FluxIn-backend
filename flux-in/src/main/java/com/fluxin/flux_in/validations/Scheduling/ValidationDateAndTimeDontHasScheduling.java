package com.fluxin.flux_in.validations.Scheduling;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import com.fluxin.flux_in.model.Procedure;
import com.fluxin.flux_in.repository.ProcedureRepository;
import com.fluxin.flux_in.repository.SchedulingRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidationDateAndTimeDontHasScheduling implements ValidationScheduling{
    @Autowired
    private SchedulingRepository schedulingRepository;
    @Autowired
    private ProcedureRepository procedureRepository;

    public void validation(CreateSchedulingDTO schedulingDTO) {
        var procedures = procedureRepository.findByIdIn(schedulingDTO.proceduresIDs());
        int durationInMinutes = procedures.stream().mapToInt(Procedure::getDuration).sum();
        LocalDateTime endOfSchedule = schedulingDTO.date().plusMinutes(durationInMinutes);
        if(schedulingRepository.existsSchedulingToEmployeeInInterval(schedulingDTO.employeeID(),schedulingDTO.date(),endOfSchedule)){
            throw new ValidationException("Horário indisponivel para este funcionário.");
        }
    }
}
