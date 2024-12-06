package com.fluxin.flux_in.dto.employeeDTO;

import com.fluxin.flux_in.model.Employee;

public record EmployeeDTO(Long id, String name, String telephone, String email, WorkingHourDTO workingHour) {
    public EmployeeDTO(Employee employee) {
        this(employee.getId(), employee.getName(), employee.getTelephone(), employee.getEmail(), new WorkingHourDTO(employee.getWorkingHour()));
    }
}