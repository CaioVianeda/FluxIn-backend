package com.fluxin.flux_in.service;

import com.fluxin.flux_in.dto.clientDTO.ClientDTO;
import com.fluxin.flux_in.dto.clientDTO.CreateClientDTO;
import com.fluxin.flux_in.model.Client;
import com.fluxin.flux_in.repository.ClientRepository;
import com.fluxin.flux_in.repository.EstablishmentRepository;
import com.fluxin.flux_in.validations.Client.ValidationClient;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private List<ValidationClient>  validationClient;

    @Transactional
    public ClientDTO createClient(Long id, CreateClientDTO clientDTO) {
        validationClient.forEach(v -> v.validation(clientDTO));
        var establishment = establishmentRepository.findById(id);
        establishment.orElseThrow(() -> new EntityNotFoundException("Estabelecimento com ID " + id + " não encontrado."));
        Client client = new Client(clientDTO, establishment.get());
        clientRepository.save(client);
        return new ClientDTO(client);
    }

    public Set<ClientDTO> getAllClientsByEstablishmentID(Long id) {
        return clientRepository.findByEstablishmentId(id).stream().map(ClientDTO::new).collect(Collectors.toSet());
    }

    public ClientDTO getClientByID(Long id) {
        var client = clientRepository.findById(id);
        client.orElseThrow(() -> new EntityNotFoundException("Estabelecimento com ID " + id + " não encontrado."));
        return new ClientDTO(client.get());
    }

    @Transactional
    public ClientDTO updateClientByID(Long id, CreateClientDTO clientDTO) {
        validationClient.forEach(v -> v.validation(clientDTO));
        var client = clientRepository.findById(id);
        client.orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado."));
        client.get().setName(clientDTO.name().trim().toLowerCase());
        client.get().setTelephone(clientDTO.telephone().trim().replace("-", ""));
        clientRepository.save(client.get());
        return new ClientDTO(client.get());
    }

    public void deleteClientByID(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado.");
        }
    }
}
