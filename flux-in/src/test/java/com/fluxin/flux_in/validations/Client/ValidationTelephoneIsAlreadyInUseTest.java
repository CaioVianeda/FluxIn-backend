package com.fluxin.flux_in.validations.Client;

import com.fluxin.flux_in.dto.clientDTO.CreateClientDTO;
import com.fluxin.flux_in.repository.ClientRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidationTelephoneIsAlreadyInUseTest {

    @InjectMocks
    private ValidationTelephoneIsAlreadyInUse validation;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CreateClientDTO clientDTO;

    @Test
    @DisplayName("Deveria lançar uma ValidationException ao passar um telefone já associado a um cliente.")
    void validationTelephoneIsAlreadyInUseScenario01() {
        BDDMockito.given(clientRepository.existsByTelephone(BDDMockito.anyString())).willReturn(true);
        BDDMockito.given(clientDTO.id()).willReturn(null);
        BDDMockito.given(clientDTO.telephone()).willReturn("41999999999");
        Assertions.assertThrows(ValidationException.class, () -> validation.validation(clientDTO));
    }

    @Test
    @DisplayName("Deveria fazer nada ao passar um telefone que não esteja associado a um cliente.")
    void validationTelephoneIsAlreadyInUseScenario02() {
        validation.validation(clientDTO);
    }
}