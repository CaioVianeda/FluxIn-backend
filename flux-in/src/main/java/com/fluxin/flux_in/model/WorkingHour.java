package com.fluxin.flux_in.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class WorkingHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime startOfWorkDay;
    private LocalTime endOfWorkDay;
    private LocalTime startInterval;
    private Integer intervalDuration;
    @OneToOne
    private Employee employee;
}
