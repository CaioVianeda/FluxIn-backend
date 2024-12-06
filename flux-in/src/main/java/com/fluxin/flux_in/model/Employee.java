package com.fluxin.flux_in.model;

import com.fluxin.flux_in.dto.CreateEmployeeDTO;
import com.fluxin.flux_in.dto.EmployeeDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        this.name = employeeDTO.name();
        this.telephone = employeeDTO.telephone();
        this.email = employeeDTO.email();
        this.establishment = establishment;
    }
}
