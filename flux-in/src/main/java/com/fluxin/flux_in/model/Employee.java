package com.fluxin.flux_in.model;

import com.fluxin.flux_in.dto.employeeDTO.CreateEmployeeDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String telephone;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    private WorkingHour workingHour;
    @ManyToOne
    private Establishment establishment;
    @OneToMany(mappedBy = "employee")
    private Set<Scheduling> schedulings;

    public Employee(CreateEmployeeDTO employeeDTO, Establishment establishment) {
        this.name = employeeDTO.name().trim().toLowerCase();
        this.telephone = employeeDTO.telephone().trim().toLowerCase();
        this.email = employeeDTO.email().trim().toLowerCase();
        this.establishment = establishment;
    }

    public Boolean isWorkingday(Integer day){
        return this.workingHour.getWorkingDays().stream().anyMatch(workingDay -> Objects.equals(workingDay, day));
    }

    public Boolean isInterval(LocalTime time){
        var endInterval = workingHour.getStartInterval().plusMinutes(workingHour.getIntervalDuration());
        return !time.isBefore(workingHour.getStartInterval()) && !time.isAfter(endInterval);
    }
}
