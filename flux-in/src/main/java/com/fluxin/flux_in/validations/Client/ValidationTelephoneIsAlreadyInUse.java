package com.fluxin.flux_in.validations.Client;

import com.fluxin.flux_in.dto.clientDTO.CreateClientDTO;
import com.fluxin.flux_in.repository.ClientRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationTelephoneIsAlreadyInUse implements ValidationClient {

    @Autowired
    private ClientRepository clientRepository;

    public void validation(CreateClientDTO clientDTO) {
        if (clientRepository.existsByTelephone(clientDTO.telephone().trim().replace("-", "")))
            throw new ValidationException("Este telefone já está atribuido à um cliente.");
    }
}
