package com.fluxin.flux_in.model;

import com.fluxin.flux_in.dto.procedureDTO.CreateProcedureDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer duration;
    private Boolean active;
    @ManyToOne
    private Establishment establishment;
    @ManyToMany(mappedBy = "procedures")
    private List<Scheduling> schedulings;

    public Procedure(CreateProcedureDTO procedureDTO, Establishment establishment) {
        this.name = procedureDTO.name().trim().toLowerCase();
        this.price = procedureDTO.price();
        this.duration = procedureDTO.duration();
        this.active = true;
        this.establishment = establishment;
        this.schedulings = new ArrayList<>();
    }
}
