package com.generic.khatabook.room.service.controller;

import com.generic.khatabook.room.service.model.HostalDTO;
import com.generic.khatabook.room.service.service.HostalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hostal")
@AllArgsConstructor
public class HostalController {

    private final HostalService hostalService;

    @GetMapping
    public ResponseEntity<List<HostalDTO>> hostals() {
        return ResponseEntity.ok(hostalService.getAll());
    }

    @PostMapping
    public ResponseEntity<HostalDTO> createHostal(@RequestBody HostalDTO hostalDTO) {

        hostalService.saveOrUpdate(hostalDTO);
        return ResponseEntity.ok(hostalService.getHostalById(hostalDTO.id()));

    }

    @GetMapping("{id}")
    public ResponseEntity<HostalDTO> hostalById(@RequestParam int id) {
        return ResponseEntity.ok(hostalService.getHostalById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HostalDTO> deleteHostalById(@RequestParam int id) {
        return ResponseEntity.ok(hostalService.getHostalById(id));
    }

    @GetMapping("{name}")
    public ResponseEntity<List<HostalDTO>> hostalByName(@RequestParam String name) {

        return ResponseEntity.ok(hostalService.getHostalByName(name));

    }


}
