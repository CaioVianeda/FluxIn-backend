package com.fluxin.flux_in.service;

import com.fluxin.flux_in.dto.CreateEmployeeDTO;
import com.fluxin.flux_in.dto.EmployeeDTO;
import com.fluxin.flux_in.dto.UpdateEmployeeInformationsDTO;
import com.fluxin.flux_in.dto.WorkingHourDTO;
import com.fluxin.flux_in.model.Employee;
import com.fluxin.flux_in.model.WorkingHour;
import com.fluxin.flux_in.repository.EmployeeRepository;
import com.fluxin.flux_in.repository.EstablishmentRepository;
import com.fluxin.flux_in.repository.WorkingHourRepository;
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

    @Transactional
    public EmployeeDTO createEmployee(Long id, CreateEmployeeDTO employeeDTO) {
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
        employee.get().setName(employeeInformationsDTO.name());
        employee.get().setTelephone(employeeInformationsDTO.telephone());
        employee.get().setEmail(employeeInformationsDTO.email());
        employeeRepository.save(employee.get());
        return new EmployeeDTO(employee.get());
    }

    @Transactional
    public WorkingHourDTO updateEmployeeWorkingHour(Long id, WorkingHourDTO workingHourDTO) {
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
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else throw new EntityNotFoundException("Funcionário com ID " + id + " não encontrado.");
    }
}
