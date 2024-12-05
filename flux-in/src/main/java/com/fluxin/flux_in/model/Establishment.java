package com.fluxin.flux_in.model;

import com.fluxin.flux_in.dto.CreateEstablishmentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "establishment")
    private Set<Employee> employees;
    @OneToMany(mappedBy = "establishment")
    private Set<Client> clients;
    @OneToMany(mappedBy = "establishment")
    private Set<Procedure> procedures;


    public Establishment(String name, String email) {
        this.name = name;
        this.email = email;
        this.employees = new HashSet<>();
        this.clients = new HashSet<>();
        this.procedures = new HashSet<>();
    }

    public Establishment(CreateEstablishmentDTO establishmentDTO){
        this.name = establishmentDTO.name();
        this.email = establishmentDTO.email();
        this.employees = new HashSet<>();
        this.clients = new HashSet<>();
        this.procedures = new HashSet<>();
    }
}
