package com.fluxin.flux_in.repository;

import com.fluxin.flux_in.dto.clientDTO.CreateClientDTO;
import com.fluxin.flux_in.dto.employeeDTO.CreateEmployeeDTO;
import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
import com.fluxin.flux_in.dto.establishmentDTO.CreateEstablishmentDTO;
import com.fluxin.flux_in.dto.procedureDTO.CreateProcedureDTO;
import com.fluxin.flux_in.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class SchedulingRepositoryTest {

    @Autowired
    private SchedulingRepository schedulingRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve devolver true quando existe agendamento entre os horarios.")
    void existsSchedulingToEmployeeInIntervalScenario1() {
        var establishment = createEstablishment();
        var employee = createEmployee(establishment);
        var scheduling = createScheduling(establishment, employee);
        var existsScheduling = schedulingRepository.existsSchedulingToEmployeeInInterval(employee.getId(),scheduling.getDate().plusMinutes(-60),scheduling.getDate().plusMinutes(60));
        assertThat(existsScheduling).isTrue();
    }

    @Test
    @DisplayName("Deve devolver false quando não existe agendamento entre os horarios.")
    void existsSchedulingToEmployeeInIntervalScenario2() {
        var establishment = createEstablishment();
        var employee = createEmployee(establishment);
        var existsScheduling = schedulingRepository.existsSchedulingToEmployeeInInterval(employee.getId(),LocalDateTime.now().plusMinutes(-60),LocalDateTime.now().plusMinutes(60));
        assertThat(existsScheduling).isFalse();
    }

    private Scheduling createScheduling(Establishment establishment, Employee employee) {
        var client = createClient(establishment);
        List<Procedure> procedures = createProcedure(establishment);
        Scheduling scheduling = new Scheduling(establishment, client, employee, procedures, LocalDateTime.now());
        em.persist(scheduling);
        em.flush();
        return scheduling;
    }

    private Establishment createEstablishment() {
        var establishment = new Establishment(establishmentDTO());
        em.persist(establishment);
        return establishment;
    }

    private CreateEstablishmentDTO establishmentDTO() {
        return new CreateEstablishmentDTO("Estabelecimento Teste", "teste@gmail.com");
    }

    private Client createClient(Establishment establishment) {
        var client = new Client(clientDTO(), establishment);
        em.persist(client);
        return client;
    }

    private CreateClientDTO clientDTO() {
        return new CreateClientDTO(null, "Cliente Teste", "(22)222222222");
    }

    private Employee createEmployee(Establishment establishment) {
        var workingHour = createWorkingHour();
        var employee = new Employee(employeeDTO(), establishment);
        employee.setWorkingHour(workingHour);
        em.persist(employee);
        return employee;
    }

    private CreateEmployeeDTO employeeDTO() {
        return new CreateEmployeeDTO("Tester", "(11)111111111", "tester@gmail.com", workingHourDTO());
    }

    private WorkingHour createWorkingHour() {
        var workingHour = new WorkingHour(workingHourDTO());
        em.persist(workingHour);
        return  workingHour;
    }

    private WorkingHourDTO workingHourDTO() {
        LocalTime startOfWorkday = LocalTime.of(8, 0);
        LocalTime endOfWorkday = LocalTime.of(17, 0);
        LocalTime startInterval = LocalTime.of(12, 0);
        Integer intervalDuration = 60;
        Set<Integer> workingDays = Set.of(1, 2, 3, 4, 5);
        return new WorkingHourDTO(startOfWorkday, endOfWorkday, startInterval, intervalDuration, workingDays);
    }

    private List<Procedure> createProcedure(Establishment establishment) {
        var procedures = new ArrayList<Procedure>();
        procedures.add(new Procedure(procedureDTO(), establishment));
        procedures.forEach(p -> em.persist(p));
        return procedures;
    }

    private CreateProcedureDTO procedureDTO() {
        return new CreateProcedureDTO("procedimento", new BigDecimal(10), 30);
    }

}