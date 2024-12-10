package com.fluxin.flux_in.model;

import com.fluxin.flux_in.dto.clientDTO.CreateClientDTO;
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
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String telephone;
    @ManyToOne
    private Establishment establishment;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Scheduling> schedulings;

    public Client(CreateClientDTO clientDTO, Establishment establishment) {
        this.name = clientDTO.name().trim().toLowerCase();
        this.telephone = clientDTO.telephone().trim().replace("-", "");
        this.establishment = establishment;
        this.schedulings = new HashSet<>();
    }

    public Client(String name, String telephone) {
        this.name = name;
        this.telephone = telephone;
    }
}
