package com.fluxin.flux_in.model;

import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Set;

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
    private Set<Integer> workingDays;
    @OneToOne(fetch = FetchType.LAZY)
    private Employee employee;

    public WorkingHour(WorkingHourDTO workingHourDTO) {
        this.startOfWorkDay = workingHourDTO.startOfWorkday();
        this.endOfWorkDay = workingHourDTO.endOfWorkday();
        this.startInterval = workingHourDTO.startInterval();
        this.intervalDuration = workingHourDTO.intervalDuration();
        this.workingDays = workingHourDTO.workingDays();
    }
}
