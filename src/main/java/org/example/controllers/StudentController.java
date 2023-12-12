package org.example.controllers;


import jakarta.persistence.EntityNotFoundException;
import org.example.model.Students;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Students> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Students> getStudentById(@PathVariable Integer id) {
        Students student = studentService.getStudentById(id).orElse(null);
        return (student != null) ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();

    }

    @PostMapping("/addStudent")
    public ResponseEntity<?> addStudent(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String cnp,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam Integer studyYear,
            @RequestParam String studyLevel,
            @RequestParam String fundingForm,
            @RequestParam String graduatedHighSchool) {

        try {
            Students addedStudent = studentService.addStudent(
                    firstName, lastName, cnp, birthDate, studyYear, studyLevel, fundingForm, graduatedHighSchool);

            return ResponseEntity.status(HttpStatus.CREATED).body(addedStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Students> updateStudent(@PathVariable Integer id, @RequestBody Students updatedStudent) {
        Optional<Students> existingStudent = studentService.updateStudent(id, updatedStudent);

        return existingStudent.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
