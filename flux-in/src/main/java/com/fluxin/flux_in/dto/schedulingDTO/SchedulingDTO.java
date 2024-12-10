package com.fluxin.flux_in.dto.schedulingDTO;

import com.fluxin.flux_in.dto.clientDTO.ClientDTO;
import com.fluxin.flux_in.dto.employeeDTO.ResumeEmployeeDTO;
import com.fluxin.flux_in.dto.procedureDTO.ResumeProcedureDTO;
import com.fluxin.flux_in.model.Scheduling;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SchedulingDTO(Long id, ClientDTO client, ResumeEmployeeDTO employee, List<ResumeProcedureDTO> procedures, LocalDateTime date,
                            Boolean finished) {
    public SchedulingDTO(Scheduling scheduling) {
        this(scheduling.getId(), new ClientDTO(scheduling.getClient()), new ResumeEmployeeDTO(scheduling.getEmployee()),
                scheduling.getProcedures().stream().map(ResumeProcedureDTO::new).collect(Collectors.toList()),
                scheduling.getDate(),
                scheduling.getFinished());
    }
}
