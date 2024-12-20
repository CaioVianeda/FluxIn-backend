package com.fluxin.flux_in.controller;

import com.fluxin.flux_in.dto.procedureDTO.CreateProcedureDTO;
import com.fluxin.flux_in.dto.procedureDTO.ProcedureDTO;
import com.fluxin.flux_in.service.ProcedureService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("procedure")
@SecurityRequirement(name = "bearer-key")
public class ProcedureController {
    @Autowired
    private ProcedureService procedureService;

    @PostMapping("/{id}")
    public ResponseEntity<ProcedureDTO> createProcedure(@PathVariable("id") Long id, @RequestBody @Valid CreateProcedureDTO procedureDTO, UriComponentsBuilder uriBuilder){
        var procedure = procedureService.createProcedure(id, procedureDTO);
        var uri = uriBuilder.path("procedure/{id}").buildAndExpand(procedure.id()).toUri();
        return ResponseEntity.created(uri).body(procedure);
    }

    @GetMapping("/{id}/all")
    public ResponseEntity<List<ProcedureDTO>> getAllProceduresByEstablishmentID(@PathVariable("id") Long id){
        var procedures = procedureService.getAllProceduresByEstablishmentID(id);
        return ResponseEntity.ok(procedures);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcedureDTO> getProcedureByID(@PathVariable("id") Long id){
        var procedure = procedureService.getProcedureByID(id);
        return ResponseEntity.ok(procedure);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcedureDTO> updateProcedure(@PathVariable("id") Long id, @RequestBody @Valid CreateProcedureDTO procedureDTO){
        var procedure = procedureService.updateProcedureByID(id, procedureDTO);
        return ResponseEntity.ok(procedure);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProcedureByID(@PathVariable("id") Long id){
        procedureService.deleteProcedureByID(id);
        return  ResponseEntity.noContent().build();
    }
}
