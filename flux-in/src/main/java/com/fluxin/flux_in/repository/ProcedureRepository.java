package com.fluxin.flux_in.repository;

import com.fluxin.flux_in.model.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcedureRepository extends JpaRepository<Procedure,Long> {
    List<Procedure> findByIdIn(List<Long> ids);
    List<Procedure> findByEstablishmentId(Long id);
}