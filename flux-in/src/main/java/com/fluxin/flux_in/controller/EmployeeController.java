package com.fluxin.flux_in.controller;

import com.fluxin.flux_in.dto.employeeDTO.CreateEmployeeDTO;
import com.fluxin.flux_in.dto.employeeDTO.EmployeeDTO;
import com.fluxin.flux_in.dto.employeeDTO.UpdateEmployeeInformationsDTO;
import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
import com.fluxin.flux_in.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/{id}")
    public ResponseEntity<EmployeeDTO> createEmployee(@PathVariable("id") Long id, @RequestBody @Valid CreateEmployeeDTO employeeDTO, UriComponentsBuilder uriBuilder) {
        var employee = employeeService.createEmployee(id, employeeDTO);
        var uri = uriBuilder.path("employee/{id}").buildAndExpand(employee.id()).toUri();
        return ResponseEntity.created(uri).body(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long id) {
        var employee = employeeService.getEmployeesById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeInformations(@PathVariable("id") Long id, @RequestBody @Valid UpdateEmployeeInformationsDTO employeeInformationsDTO) {
        var employee = employeeService.updateEmployeeInformations(id, employeeInformationsDTO);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}/working-hour")
    public ResponseEntity<WorkingHourDTO> updateEmployeeWorkHour(@PathVariable("id") Long id, @RequestBody @Valid WorkingHourDTO workingHourDTO) {
        var workingHour = employeeService.updateEmployeeWorkingHour(id, workingHourDTO);
        return ResponseEntity.ok(workingHour);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}