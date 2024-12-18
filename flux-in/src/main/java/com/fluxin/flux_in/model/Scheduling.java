package com.fluxin.flux_in.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Establishment establishment;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;
    @ManyToMany
    @JoinTable(name = "scheduling_procedure",
            joinColumns = @JoinColumn(name = "scheduling_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id"))
    private List<Procedure> procedures;
    private LocalDateTime date;
    private Boolean finished;

    public Scheduling(Establishment establishment ,Client client, Employee employee, List<Procedure> procedures, LocalDateTime date) {
        this.establishment = establishment;
        this.client = client;
        this.employee = employee;
        this.procedures = procedures;
        this.date = date;
        this.finished = false;
    }
}
