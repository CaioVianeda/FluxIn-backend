package com.fluxin.flux_in.service;

import com.fluxin.flux_in.dto.employeeDTO.CreateEmployeeDTO;
import com.fluxin.flux_in.dto.employeeDTO.EmployeeDTO;
import com.fluxin.flux_in.dto.employeeDTO.WorkingHourDTO;
import com.fluxin.flux_in.dto.establishmentDTO.CreateEstablishmentDTO;
import com.fluxin.flux_in.dto.establishmentDTO.EstablishmentDTO;
import com.fluxin.flux_in.model.Employee;
import com.fluxin.flux_in.model.Establishment;
import com.fluxin.flux_in.model.WorkingHour;
import com.fluxin.flux_in.repository.EstablishmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstablishmentServiceTest {

    @InjectMocks
    private EstablishmentService service;

    @Mock
    private EstablishmentRepository establishmentRepository;

    @Mock
    private EmployeeService employeeService;

    private CreateEstablishmentDTO establishmentDTO;

    @Mock
    private Establishment establishment;

    @Test
    @DisplayName("Deveria salvar o estabelecimento ao chamar o método.")
    void createEstablishmentScenario01() {
        this.establishmentDTO = createEstablishmentDTO();

        EstablishmentDTO establishmentCreatedDTO = service.createEstablishment(establishmentDTO);

        then(establishmentRepository).should().save(any());

        assertEquals(establishmentDTO.name(), establishmentCreatedDTO.name());
        assertEquals(establishmentDTO.email(), establishmentCreatedDTO.email());
    }

    @Test
    @DisplayName("Deveria retornar uma lista de estabelecimentos.")
    void getEstablishmentScenario01() {
        Establishment establishment1 = new Establishment();
        establishment1.setName("Establishment 1");
        establishment1.setEmail("establishment1@example.com");

        Establishment establishment2 = new Establishment();
        establishment2.setName("Establishment 2");
        establishment2.setEmail("establishment2@example.com");

        given(establishmentRepository.findAll()).willReturn(Arrays.asList(establishment1, establishment2));

        List<EstablishmentDTO> establishmentDTOList = service.getEstablishments();


        assertNotNull(establishmentDTOList);
        assertEquals(2, establishmentDTOList.size());

        EstablishmentDTO dto1 = establishmentDTOList.get(0);
        assertEquals("Establishment 1", dto1.name());
        assertEquals("establishment1@example.com", dto1.email());

        EstablishmentDTO dto2 = establishmentDTOList.get(1);
        assertEquals("Establishment 2", dto2.name());
        assertEquals("establishment2@example.com", dto2.email());
    }

    @Test
    @DisplayName("Deveria retornar um estabelecimento ao passar o ID.")
    void getEstablishmentByIDScenario01() {
        Establishment establishment = new Establishment(createEstablishmentDTO());
        establishment.setId(1L);

        given(establishmentRepository.findById(1L)).willReturn(Optional.of(establishment));

        assertNotNull(establishmentRepository.findById(1L).get());
        assertEquals(establishment, establishmentRepository.findById(1L).get());
        assertEquals(new EstablishmentDTO(establishment), service.getEstablishmentById(1L));
    }

    @Test
    @DisplayName("Deveria lançar um EntityNotFound ao buscar por ID inexistente de um estabelecimento.")
    void getEstablishmentByIDScenario02() {
        assertThrows(EntityNotFoundException.class, () -> service.getEstablishmentById(1L));
    }

    @Test
    @DisplayName("Deveria atualizar um estabelecimento.")
    void updateEstablishmentScenario01() {

        Establishment establishment1 = new Establishment();
        establishment1.setName("Establishment 1");
        establishment1.setEmail("establishment1@example.com");

        given(establishmentRepository.findById(any())).willReturn(Optional.of(establishment1));

        this.establishmentDTO = createEstablishmentDTO();

        EstablishmentDTO establishmentUpdatedDTO = service.updateEstablishment(any(), establishmentDTO);

        then(establishmentRepository).should().save(any());

        assertEquals(establishmentDTO.name(), establishmentUpdatedDTO.name());
        assertEquals(establishmentDTO.email(), establishmentUpdatedDTO.email());
    }

    @Test
    @DisplayName("Deveria lançar um EntityNotFound ao buscar um ID de estabelecimento inxeistente.")
    void updateEstablishmentScenario02() {
        assertThrows(EntityNotFoundException.class, () -> service.updateEstablishment(1L, establishmentDTO));
    }

    @Test
    @DisplayName("Deveria excluir um estabelecimento.")
    void deleteEstablishmentScenario01() {
        given(establishmentRepository.existsById(any())).willReturn(true);
        service.deleteEstablishment(any());
        verify(establishmentRepository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Deveria lançar um EntityNotFound ao tentar excluir um estabelecimento com ID inexistente.")
    void deleteEstablishmentScenario02() {
        assertThrows(EntityNotFoundException.class, () -> service.deleteEstablishment(1L));
    }

    @Test
    @DisplayName("Deveria retornar uma lista de funcionários associados ao estabelecimento.")
    void getEmployeesScenario01 (){

        Establishment establishment = new Establishment();
        establishment.setId(1L);
        establishment.setName("establishment name");
        establishment.setEmail("establishment@gmail.com");

        Employee employee1 = createEmployee("John Doe", "john.doe@example.com", establishment);
        Employee employee2 = createEmployee("Jane Doe", "jane.doe@example.com", establishment);

        List<Employee> employees = Arrays.asList(employee1, employee2);

        given(employeeService.getEmployeesByEstablishmentId(any())).willReturn(employees.stream().map(EmployeeDTO::new).collect(Collectors.toList()));

        List<EmployeeDTO> result = service.getEmployees(any());

        verify(employeeService, times(1)).getEmployeesByEstablishmentId(any());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).name());
        assertEquals("Jane Doe", result.get(1).name());

    }

    private Employee createEmployee(String name, String email, Establishment establishment) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        employee.setEstablishment(establishment);
        employee.setWorkingHour(createWorkingHour());
        return employee;
    }

    private WorkingHour createWorkingHour(){
        return new WorkingHour(new WorkingHourDTO(LocalTime.of(8,0), LocalTime.of(17,0),LocalTime.of(12,0),60,
                Set.of(1,2,3,4,5)));
    }

    private CreateEstablishmentDTO createEstablishmentDTO() {
        return new CreateEstablishmentDTO("establishment test", "test@fluxin.com");
    }
}