package com.fluxin.flux_in.controller;

import com.fluxin.flux_in.dto.schedulingDTO.CreateSchedulingDTO;
import com.fluxin.flux_in.dto.schedulingDTO.SchedulingDTO;
import com.fluxin.flux_in.service.SchedulingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("scheduling")
@SecurityRequirement(name = "bearer-key")
public class SchedulingController {
    @Autowired
    private SchedulingService schedulingService;

    @PostMapping("")
    public ResponseEntity<SchedulingDTO> toSchedule(@RequestBody @Valid CreateSchedulingDTO schedulingDTO, UriComponentsBuilder uriBuilder){
        var schedule = schedulingService.createScheduling(schedulingDTO);
        var uri = uriBuilder.path("/scheduling/{id}").buildAndExpand(schedule.id()).toUri();
        return ResponseEntity.created(uri).body(schedule);
    }

    @GetMapping("/employee/{id}/all")
    public ResponseEntity<List<SchedulingDTO>> getAllSchedulingByEmployeeID(@PathVariable("id") Long id){
        var schedules = schedulingService.getAllSchedulingsByEmployeeID(id);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedulingDTO> getSchedulingByID(@PathVariable("id") Long id){
        var scheduling = schedulingService.getSchedulingByID(id);
        return ResponseEntity.ok(scheduling);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchedulingDTO> updateScheduling(@PathVariable("id") Long id, @RequestBody @Valid CreateSchedulingDTO schedulingDTO){
        var scheduling = schedulingService.updateSchedulingByID(id, schedulingDTO);
        return ResponseEntity.ok(scheduling);
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<SchedulingDTO> finishScheduling(@PathVariable("id") Long id){
        var scheduling = schedulingService.finishSchedulingByID(id);
        return ResponseEntity.ok(scheduling);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSchedulingByID(@PathVariable("id") Long id){
        schedulingService.deleteSchedulingByID(id);
        return ResponseEntity.noContent().build();
    }
}
