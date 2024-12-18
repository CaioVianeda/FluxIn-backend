package com.fluxin.flux_in.repository;

import com.fluxin.flux_in.model.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SchedulingRepository extends JpaRepository<Scheduling,Long> {
    List<Scheduling> findByEmployeeId(Long id);
    List<Scheduling> findByEmployeeIdAndDateBetween(Long id, LocalDateTime start, LocalDateTime end);
    @Query("SELECT COUNT(s) > 0 FROM Scheduling s WHERE s.employee.id = :employeeId " +
            "AND s.date > :startDate AND s.date < :endDate")
    Boolean existsSchedulingToEmployeeInInterval(@Param("employeeId") Long employeeId,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

}
