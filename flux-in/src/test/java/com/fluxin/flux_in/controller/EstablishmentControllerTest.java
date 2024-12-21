package com.fluxin.flux_in.controller;

import com.fluxin.flux_in.dto.establishmentDTO.CreateEstablishmentDTO;
import com.fluxin.flux_in.dto.establishmentDTO.EstablishmentDTO;
import com.fluxin.flux_in.service.EstablishmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class EstablishmentControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<CreateEstablishmentDTO> createEstablishmentDTOJacksonTester;

    @Autowired
    private JacksonTester<EstablishmentDTO> establishmentDTOJacksonTester;


    @Test
    @DisplayName("Deveria retorna codigo http 400 quando informacoes estao invalidas.")
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
        var response = mvc.perform(post("/establishment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createEstablishmentDTOJacksonTester.write(
                                new CreateEstablishmentDTO("estabelecimento teste", "estabelecimento@gmail.com")
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        var expectedJson = establishmentDTOJacksonTester.write(
                new EstablishmentDTO(null, "estabelecimento teste","estabelecimento@gmail.com" )
        ).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
//        assertThat(response.getContentAsString()).matches(".*\"name\":\"estabelecimento teste\".*");
//        assertThat(response.getContentAsString()).matches(".*\"email\":\"estabelecimento@gmail.com\".*");
    }

    @Test
    void getEstablishmentsScenario1() {
    }

    @Test
    void getEstablishmentById() {
    }

    @Test
    void updateEstablishmentById() {
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
    void deleteEstablishmentById() {
    }

    @Test
    void getEmployees() {
    }
}