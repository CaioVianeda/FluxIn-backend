package com.fluxin.flux_in.service;

import com.fluxin.flux_in.dto.clientDTO.ClientDTO;
import com.fluxin.flux_in.dto.clientDTO.CreateClientDTO;
import com.fluxin.flux_in.model.Client;
import com.fluxin.flux_in.model.Establishment;
import com.fluxin.flux_in.model.Scheduling;
import com.fluxin.flux_in.repository.ClientRepository;
import com.fluxin.flux_in.repository.EstablishmentRepository;
import com.fluxin.flux_in.validations.Client.ValidationClient;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private Scheduling scheduling;

    @Mock
    private EstablishmentRepository establishmentRepository;

    @Spy
    private List<ValidationClient> validationClient = new ArrayList<>();

    @Mock
    private ValidationClient validationClient1;

    @Mock
    private ValidationClient validationClient2;

    @Captor
    private ArgumentCaptor<Client> clientArgumentCaptor;

    @Test
    @DisplayName("Deveria salvar o cliente. ")
    void createClientScenario01() {

        var dto = createClientDTO();

        BDDMockito.given(establishmentRepository.findById(any())).willReturn(Optional.of(new Establishment()));

        var createdClient = clientService.createClient(1L, dto);

        BDDMockito.then(clientRepository).should().save(clientArgumentCaptor.capture());
        assertEquals(createdClient.name(), createClientDTO().name());
        assertEquals(createdClient.telephone(), createClientDTO().telephone());

    }

    @Test
    @DisplayName("Deveria lançar um EntityNotFound ao não encontrar um estabelecimento com o ID. ")
    void createClientScenario02() {
        var dto = createClientDTO();
        Assertions.assertThrows(EntityNotFoundException.class, () -> clientService.createClient(1L, dto));
    }

    @Test
    @DisplayName("Deveria chamar a validação de cliente. ")
    void createClientScenario03() {

        var dto = createClientDTO();

        BDDMockito.given(establishmentRepository.findById(any())).willReturn(Optional.of(new Establishment()));
        validationClient.add(validationClient1);
        validationClient.add(validationClient2);

        clientService.createClient(1L, dto);

        BDDMockito.then(validationClient1).should().validation(dto);
        BDDMockito.then(validationClient2).should().validation(dto);
    }

    @Test
    @DisplayName("Deveria retornar uma lista de clientes.")
    void getAllClientsByEstablishmentIDScenario01() {
        Long establishmentId = 1L;

        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("Cliente 1");
        client1.setTelephone("11888888888");

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Cliente 2");
        client2.setTelephone("11888888888");

        List<Client> clientList = List.of(client1, client2);

        BDDMockito.given(clientRepository.findByEstablishmentId(establishmentId)).willReturn(clientList);

        Set<ClientDTO> clientDTOSet = clientService.getAllClientsByEstablishmentID(establishmentId);

        assertEquals(2, clientDTOSet.size());

        List<ClientDTO> clientDTOList = clientDTOSet.stream().toList();

        assertEquals(client1.getId(), clientDTOList.get(0).id());
        assertEquals(client1.getName(), clientDTOList.get(0).name());
        assertEquals(client1.getTelephone(), clientDTOList.get(0).telephone());

        assertEquals(client2.getId(), clientDTOList.get(1).id());
        assertEquals(client2.getName(), clientDTOList.get(1).name());
        assertEquals(client2.getTelephone(), clientDTOList.get(1).telephone());
    }

    @Test
    @DisplayName("Deveria retornar uma lista de clientes.")
    void getAllClientByIDScenario01() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("Cliente 1");
        client1.setTelephone("11888888888");

        BDDMockito.given(clientRepository.findById(any())).willReturn(Optional.of(client1));

        ClientDTO clientDTO = clientService.getClientByID(client1.getId());

        assertEquals(client1.getId(), clientDTO.id());
        assertEquals(client1.getName(), clientDTO.name());
    }


    @Test
    @DisplayName("Deveria lançar uma EntityNotFound ao passar um ID de cliente inexistente.")
    void getAllClientByIDScenario02() {
        assertThrows(EntityNotFoundException.class, () -> clientService.getClientByID(any()));
    }

    @Test
    @DisplayName("Deveria atualizar um cliente.")
    void updateClientByIDScenario01() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("Cliente 1");
        client1.setTelephone("11888888888");

        CreateClientDTO client1NewInformations = createClientDTO();

        BDDMockito.given(clientRepository.findById(any())).willReturn(Optional.of(client1));

        ClientDTO client1Updated = clientService.updateClientByID(any(), client1NewInformations);

        BDDMockito.then(clientRepository).should().save(any());

        assertEquals(client1NewInformations.name(), client1Updated.name());
        assertEquals(client1NewInformations.telephone(), client1Updated.telephone());
    }

    @Test
    @DisplayName("Deveria chamar a validação de clientes.")
    void updateClientByIDScenario02() {
        var dto = createClientDTO();

        BDDMockito.given(clientRepository.findById(any())).willReturn(Optional.of(new Client()));
        validationClient.add(validationClient1);
        validationClient.add(validationClient2);

        clientService.updateClientByID(1L, dto);

        BDDMockito.then(validationClient1).should().validation(dto);
        BDDMockito.then(validationClient2).should().validation(dto);

    }

    @Test
    @DisplayName("Deveria lançar um EntityNotFound ao informar um ID de cliente inexistente.")
    void updateClientByIDScenario03() {
        assertThrows(EntityNotFoundException.class, () -> clientService.updateClientByID(any(), createClientDTO()));
    }

    @Test
    @DisplayName("Deveria deletar um cliente.")
    void deleteClientByIDScenario01() {

        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("Cliente 1");
        client1.setTelephone("11888888888");
        client1.setSchedulings(Set.of(scheduling));

        BDDMockito.given(clientRepository.findById(any())).willReturn(Optional.of(client1));
        clientService.deleteClientByID(any());
        BDDMockito.then(clientRepository).should().deleteById(any());
    }

    private CreateClientDTO createClientDTO() {
        return new CreateClientDTO(1L, "john doe", "(11)999999999");
    }


}