package com.fluxin.flux_in.controller;

import com.fluxin.flux_in.dto.establishmentDTO.CreateEstablishmentDTO;
import com.fluxin.flux_in.dto.establishmentDTO.EstablishmentDTO;
import com.fluxin.flux_in.model.Establishment;
import com.fluxin.flux_in.service.EstablishmentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class EstablishmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private EstablishmentService establishmentService;

    @Autowired
    private JacksonTester<CreateEstablishmentDTO> createEstablishmentDTOJacksonTester;

    @Autowired
    private JacksonTester<EstablishmentDTO> establishmentDTOJacksonTester;


    @Test
    @DisplayName("Deveria retornar codigo http 400 quando informacoes estao invalidas.")
    @WithMockUser
    void createEstablishmentScenario1() throws Exception {
        var response = mvc.perform(post("/establishment"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retorna codigo http 201 quando informacoes estao validas.")
    @WithMockUser
    void createEstablishmentScenario2() throws Exception {
        var establishmentDTO = new EstablishmentDTO(1L, "estabelecimento teste", "estabelecimento@gmail.com");
        var body = new CreateEstablishmentDTO("estabelecimento teste", "estabelecimento@gmail.com");
        BDDMockito.given(establishmentService.createEstablishment(BDDMockito.any())).willReturn(establishmentDTO);
        var response = mvc.perform(post("/establishment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createEstablishmentDTOJacksonTester.write(
                                body
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).matches(".*\"name\":\"estabelecimento teste\".*");
        assertThat(response.getContentAsString()).matches(".*\"email\":\"estabelecimento@gmail.com\".*");
    }

    @Test
    @DisplayName("Deveria retornar codigo 200 ao buscar todos os estabelecimentos..")
    @WithMockUser
    void getEstablishmentsScenario1() throws Exception {
        var response = mvc.perform(get("/establishment/all")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deveria retornar codigo 200 ao passar um ID de estabelecimento existente.")
    @WithMockUser
    void getEstablishmentByIdScenario01() throws Exception {
        var dto = new CreateEstablishmentDTO("estabelecimento teste", "estabelecimento@gmail.com");

        Establishment establishment = new Establishment(dto);
        establishment.setId(1L);

        var response = mvc.perform(get("/establishment/" + establishment.getId())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("Deveria retornar codigo 404 ao passar um ID de estabelecimento inexistente.")
    @WithMockUser
    void getEstablishmentByIdScenario02() throws Exception {

        BDDMockito.given(establishmentService.getEstablishmentById(BDDMockito.anyLong())).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(get("/establishment/1")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

    @Test
    @WithMockUser
    void updateEstablishmentByIdScenario01() throws Exception {
        var establishmentDTO = new EstablishmentDTO(1L, "new estabelecimento teste", "newestabelecimento@gmail.com");
        var body = new CreateEstablishmentDTO("new estabelecimento teste", "newestabelecimento@gmail.com");
        BDDMockito.given(establishmentService.updateEstablishment( BDDMockito.anyLong(), BDDMockito.any())).willReturn(establishmentDTO);
        var response = mvc.perform(put("/establishment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createEstablishmentDTOJacksonTester.write(
                                body
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).matches(".*\"name\":\"new estabelecimento teste\".*");
        assertThat(response.getContentAsString()).matches(".*\"email\":\"newestabelecimento@gmail.com\".*");
    }

    @Test
    @DisplayName("Deveria retorna codigo http 400 quando informacoes estao invalidas.")
    @WithMockUser
    void updateEstablishmentByIdScenario1() throws Exception {
        var response = mvc.perform(put("/establishment/1"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar um notcontent ao excluir um estabelecimento.")
    @WithMockUser
    void deleteEstablishmentByIdScenario01() throws Exception {
        var response = mvc.perform(delete("/establishment/1")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @WithMockUser
    void getEmployeesScenario01() throws Exception {
        var response = mvc.perform(get("/establishment/1/employees")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}