package com.fluxin.flux_in.dto.employeeDTO;

import com.fluxin.flux_in.model.Employee;

public record ResumeEmployeeDTO(Long id, String name) {
    public ResumeEmployeeDTO(Employee employee) {
        this(employee.getId(), employee.getName());
    }
}
