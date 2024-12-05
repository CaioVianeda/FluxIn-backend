package com.fluxin.flux_in.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "establishment")
    private Set<Employee> employees;
    @OneToMany(mappedBy = "establishment")
    private Set<Client> clients;
    @OneToMany(mappedBy = "establishment")
    private List<Procedure> procedures;
}
