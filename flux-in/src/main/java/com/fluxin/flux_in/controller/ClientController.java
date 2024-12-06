package com.fluxin.flux_in.controller;

import com.fluxin.flux_in.dto.clientDTO.ClientDTO;
import com.fluxin.flux_in.dto.clientDTO.CreateClientDTO;
import com.fluxin.flux_in.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@RequestMapping("client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/{id}")
    public ResponseEntity<ClientDTO> createClient(@PathVariable("id") Long id, @RequestBody @Valid CreateClientDTO clientDTO, UriComponentsBuilder uriBuilder) {
        var client = clientService.createClient(id, clientDTO);
        var uri = uriBuilder.path("/client/{id}").buildAndExpand(client.id()).toUri();
        return ResponseEntity.created(uri).body(client);
    }

    @GetMapping("/{id}/all")
    public ResponseEntity<Set<ClientDTO>> getAllClientByEstablishmentID(@PathVariable("id") Long id) {
        var clients = clientService.getAllClientByEstablishmentID(id);
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClientByID(@PathVariable("id") Long id, @RequestBody @Valid CreateClientDTO clientDTO) {
        var client = clientService.updateClientByID(id, clientDTO);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClientByID(@PathVariable("id") Long id) {
        clientService.deleteClientByID(id);
        return ResponseEntity.noContent().build();
    }
}
