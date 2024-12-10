package com.fluxin.flux_in.service;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import com.fluxin.flux_in.dto.schedulingDTO.SchedulingDTO;
import com.fluxin.flux_in.model.Procedure;
import com.fluxin.flux_in.model.Scheduling;
import com.fluxin.flux_in.repository.*;
import com.fluxin.flux_in.validations.Scheduling.ValidationScheduling;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchedulingService {

    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private SchedulingRepository schedulingRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProcedureRepository procedureRepository;
    @Autowired
    private List<ValidationScheduling> validationSchedulings;

    public SchedulingDTO createScheduling(CreateSchedulingDTO schedulingDTO) {
        validationSchedulings.forEach(validationScheduling -> validationScheduling.validation(schedulingDTO));
        var establishment = establishmentRepository.findById(schedulingDTO.establishmentID()).orElseThrow(() -> new EntityNotFoundException("Estabelecimento com ID " + schedulingDTO.establishmentID() + " não encontrado."));
        var client = clientRepository.findById(schedulingDTO.clientID()).orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + schedulingDTO.clientID() + " não encontrado."));
        var employee = employeeRepository.findById(schedulingDTO.employeeID()).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + schedulingDTO.employeeID() + " não encontrado."));
        List<Procedure> procedures = procedureRepository.findByIdIn(schedulingDTO.proceduresIDs());
        Scheduling schedule = new Scheduling(establishment,client, employee, procedures, schedulingDTO.date());
        schedulingRepository.save(schedule);
        return new SchedulingDTO(schedule);
    }

    public List<SchedulingDTO> getAllSchedulingsByEmployeeID(Long id) {
            if (!employeeRepository.existsById(id)){
                throw new EntityNotFoundException("Funcionário com ID " + id + " não encontrado.");
            }
            return schedulingRepository.findByEmployeeId(id).stream().map(SchedulingDTO::new).collect(Collectors.toList());
    }

    public SchedulingDTO getSchedulingByID(Long id) {
        var scheduling = schedulingRepository.findById(id);
        return new SchedulingDTO(scheduling.orElseThrow(() -> new EntityNotFoundException("Agendamento com ID " + id + " não encontrado.")));
    }

    public SchedulingDTO updateSchedulingByID(Long id, CreateSchedulingDTO schedulingDTO) {
        validationSchedulings.forEach(validationScheduling -> validationScheduling.validation(schedulingDTO));
        var schedule = schedulingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agendamento com ID " + id + " não encontrado."));
        var client = clientRepository.findById(schedulingDTO.clientID()).orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + schedulingDTO.clientID() + " não encontrado."));
        schedule.setClient(client);
        var employee = employeeRepository.findById(schedulingDTO.employeeID()).orElseThrow(() -> new EntityNotFoundException("Employee com ID " + schedulingDTO.clientID() + " não encontrado."));
        schedule.setEmployee(employee);
        List<Procedure> procedures = procedureRepository.findByIdIn(schedulingDTO.proceduresIDs());
        schedule.setProcedures(procedures);
        schedule.setDate(schedulingDTO.date());
        schedulingRepository.save(schedule);
        return new SchedulingDTO(schedule);
    }

    public void deleteSchedulingByID(Long id) {
        if (schedulingRepository.existsById(id)) {
            schedulingRepository.deleteById(id);
        } else throw new EntityNotFoundException("Agendamento com ID " + id + " não encontrado.");
    }
}
