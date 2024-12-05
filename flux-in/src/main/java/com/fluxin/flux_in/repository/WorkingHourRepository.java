package com.fluxin.flux_in.repository;

import com.fluxin.flux_in.model.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingHourRepository extends JpaRepository<WorkingHour,Long> {
}
