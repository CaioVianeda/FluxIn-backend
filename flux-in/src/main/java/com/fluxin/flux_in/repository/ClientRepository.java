package com.fluxin.flux_in.repository;

import com.fluxin.flux_in.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByEstablishmentId(Long id);
    Boolean existsByTelephone(String telephone);
}
