package org.example.controller;

import org.example.enums.Countries;
import org.example.model.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.service.ProfessorService;

import java.util.List;

@RestController
@RequestMapping("/professors")
@CrossOrigin(origins = "http://localhost:8080")
public class ProfessorController{
    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addProfessor(@RequestBody Professor professor) {
        try {
            professorService.add(professor);
            return ResponseEntity.status(HttpStatus.CREATED).body(professor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Integer id) {
        return professorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Professor>> getAllProfessors() {
        List<Professor> professors = professorService.findAll();
        return ResponseEntity.ok(professors);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removeProfessor(@PathVariable Integer id) {
        try {
            professorService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProfessor(@PathVariable Integer id, @RequestBody Professor newProfessor) {
        try {
            professorService.update(id, newProfessor);
            return ResponseEntity.ok("Professor updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating professor: " + e.getMessage());
        }
    }
    @RestController
    @RequestMapping("/api/countries")
    public class CountryController {

        @GetMapping("/all")
        public Countries[] getAllCountries() {
            return Countries.values();
        }

    }

}
