package com.fluxin.flux_in.dto.clientDTO;

import com.fluxin.flux_in.model.Client;

public record ClientDTO(Long id, String name, String telephone) {
    public ClientDTO(Client client){
        this(client.getId(), client.getName(), client.getTelephone());
    }
}
