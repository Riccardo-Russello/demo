package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.model.Profession;
import com.example.demo.service.ProfessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/profession")            // Percorso base per l'API delle professioni
@RestController
public class ProfessionController {
    private final ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping
    public List<Profession> getAllProfessions() {
        return professionService.getAllProfessions();
    }

    @GetMapping(params = {"name", "surname"})
    public ProfessionByPersonResponse getProfessionByPerson(
            @RequestParam String name,
            @RequestParam String surname) {
        return professionService.getProfessionByPerson(name, surname);
    }

    @PostMapping
    public ResponseEntity<Profession> crateProfession(@Valid @RequestBody ProfessionRequest request) {
        Profession profession = professionService.createProfession(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(profession);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfession(@PathVariable UUID id) {
        professionService.deleteProfession(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Profession> updateProfessionName(@PathVariable UUID id,
                                                           @Valid @RequestBody ProfessionRequest request) {
        return ResponseEntity.ok(professionService.updateProfessionName(id, request));
    }
}
