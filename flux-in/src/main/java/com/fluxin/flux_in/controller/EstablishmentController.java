package com.fluxin.flux_in.controller;

import com.fluxin.flux_in.dto.CreateEstablishmentDTO;
import com.fluxin.flux_in.dto.EmployeeDTO;
import com.fluxin.flux_in.dto.EstablishmentDTO;
import com.fluxin.flux_in.service.EstablishmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/establishment")
public class EstablishmentController {

    @Autowired
    EstablishmentService establishmentService;

    @PostMapping
    public ResponseEntity<EstablishmentDTO> createEstablishment(@RequestBody @Valid CreateEstablishmentDTO establishmentDTO, UriComponentsBuilder uriBuilder) {
        var establishment = establishmentService.createEstablishment(establishmentDTO);
        var uri = uriBuilder.path("establishment/{id}").buildAndExpand(establishment.id()).toUri();
        return ResponseEntity.created(uri).body(establishment);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EstablishmentDTO>> getEstablishments() {
        return ResponseEntity.ok(establishmentService.getEstablishments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstablishmentDTO> getEstablishmentById(@PathVariable("id") Long id) {
        var establishment = establishmentService.getEstablishmentById(id);
        return ResponseEntity.ok(establishment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstablishmentDTO> updateEstablishmentById(@PathVariable("id") Long id, @RequestBody CreateEstablishmentDTO establishmentDTO) {
        var establishment = establishmentService.updateEstablishment(id, establishmentDTO);
        return ResponseEntity.ok(establishment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEstablishmentById(@PathVariable("id") Long id) {
        establishmentService.deleteEstablishment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@PathVariable("id") Long id) {
        var employees = establishmentService.getEmployees(id);
        return ResponseEntity.ok(employees);
    }
}