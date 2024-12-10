package com.fluxin.flux_in.service;

import com.fluxin.flux_in.dto.employeeDTO.CreateEmployeeDTO;
import com.fluxin.flux_in.dto.employeeDTO.EmployeeDTO;
import com.fluxin.flux_in.dto.employeeDTO.UpdateEmployeeInformationsDTO;
import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
import com.fluxin.flux_in.model.Employee;
import com.fluxin.flux_in.model.Scheduling;
import com.fluxin.flux_in.model.WorkingHour;
import com.fluxin.flux_in.repository.EmployeeRepository;
import com.fluxin.flux_in.repository.EstablishmentRepository;
import com.fluxin.flux_in.repository.WorkingHourRepository;
import com.fluxin.flux_in.validations.WorkingHour.ValidationWorkingHour;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    WorkingHourRepository workingHourRepository;
    @Autowired
    EstablishmentRepository establishmentRepository;
    @Autowired
    private List<ValidationWorkingHour> validationWorkingHour;

    @Transactional
    public EmployeeDTO createEmployee(Long id, CreateEmployeeDTO employeeDTO) {
        validationWorkingHour.forEach(validation -> validation.validate(employeeDTO.workingHourDTO()));
        var establishment = establishmentRepository.findById(id);
        establishment.orElseThrow(() -> new EntityNotFoundException("Estabelecimento com ID " + id + " não encontrado"));
        WorkingHour workingHour = new WorkingHour(employeeDTO.workingHourDTO());
        Employee employee = new Employee(employeeDTO, establishment.get());
        employee.setWorkingHour(workingHour);
        workingHour.setEmployee(employee);
        workingHourRepository.save(workingHour);
        employeeRepository.save(employee);
        return new EmployeeDTO(employee);
    }

    public List<EmployeeDTO> getEmployeesByEstablishmentId(Long id) {
        return employeeRepository.findByEstablishmentId(id).stream().map(EmployeeDTO::new).collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeesById(Long id) {
        return new EmployeeDTO(employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado.")));
    }

    @Transactional
    public EmployeeDTO updateEmployeeInformations(Long id, UpdateEmployeeInformationsDTO employeeInformationsDTO) {
        var employee = employeeRepository.findById(id);
        employee.orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado."));
        employee.get().setName(employeeInformationsDTO.name().trim().toLowerCase());
        employee.get().setTelephone(employeeInformationsDTO.telephone().trim().toLowerCase());
        employee.get().setEmail(employeeInformationsDTO.email().trim().toLowerCase());
        employeeRepository.save(employee.get());
        return new EmployeeDTO(employee.get());
    }

    @Transactional
    public WorkingHourDTO updateEmployeeWorkingHour(Long id, WorkingHourDTO workingHourDTO) {
        validationWorkingHour.forEach(validation -> validation.validate(workingHourDTO));
        var employee = employeeRepository.findById(id);
        employee.orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado."));
        var workingHour = workingHourRepository.findById(employee.get().getWorkingHour().getId());
        workingHour.orElseThrow(() -> new EntityNotFoundException("Expediente com ID " + id + " não encontrado."));
        workingHour.get().setStartOfWorkDay(workingHourDTO.startOfWorkday());
        workingHour.get().setEndOfWorkDay(workingHourDTO.endOfWorkday());
        workingHour.get().setStartInterval(workingHourDTO.startInterval());
        workingHour.get().setIntervalDuration(workingHourDTO.intervalDuration());
        workingHour.get().setWorkingDays(workingHourDTO.workingDays());
        workingHourRepository.save(workingHour.get());
        return new WorkingHourDTO(workingHour.get());
    }

    public void deleteEmployee(Long id) {
        var employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado."));
        for (Scheduling scheduling: employee.getSchedulings()){
            scheduling.setEmployee(null);
        }
        employeeRepository.deleteById(id);
    }
}
