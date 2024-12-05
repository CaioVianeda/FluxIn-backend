package com.fluxin.flux_in.model;

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
    @OneToOne
    private WorkingHour workingHour;
    @ManyToOne
    private Establishment establishment;
    @OneToMany(mappedBy = "employee")
    private Set<Scheduling> schedulings;
}
