package com.fluxin.flux_in.repository;

import com.fluxin.flux_in.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
