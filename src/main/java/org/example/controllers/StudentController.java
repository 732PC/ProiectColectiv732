package org.example.controllers;


import jakarta.persistence.EntityNotFoundException;
import org.example.model.HomeworkSubmission;
import org.example.model.Students;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:63342")
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

    @PostMapping("/{id}/homework-submissions")
    public ResponseEntity<?> addHomeworkSubmission(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {

        String submissionText = requestBody.get("homeworkText");

        try {
            studentService.addHomeworkSubmission(id, submissionText);
            return ResponseEntity.ok("Homework submission added successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/homework-submissions")
    public ResponseEntity<List<HomeworkSubmission>> getAllHomeworkSubmissions(@PathVariable Integer id) {
        List<HomeworkSubmission> homeworkSubmissions = studentService.getAllHomeworkSubmissions(id);
        return ResponseEntity.ok(homeworkSubmissions);
    }

    @GetMapping("/{id}/homework-submissions/{submissionId}")
    public ResponseEntity<HomeworkSubmission> getHomeworkSubmissionById(
            @PathVariable Integer id, @PathVariable Integer submissionId) {
        Optional<HomeworkSubmission> homeworkSubmission = studentService.getHomeworkSubmissionById(id, submissionId);
        return homeworkSubmission.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}/homework-submissions/{submissionId}")
    public ResponseEntity<?> deleteHomeworkSubmission(
            @PathVariable Integer id,
            @PathVariable Long submissionId) {

        try {
            boolean deleted = studentService.deleteHomeworkSubmission(id, submissionId);

            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/homework-submissions/{submissionId}")
    public ResponseEntity<String> updateHomeworkSubmission(
            @PathVariable Integer id,
            @PathVariable Integer submissionId,
            @RequestBody Map<String, Object> request) {
        return ResponseEntity.ok("Updated homework submission");
    }

    @PutMapping("/{id}/homework-submissions/{submissionId}/custom")
    public ResponseEntity<String> updateHomeworkSubmissionCustomWithId(
            @PathVariable Integer id,
            @PathVariable Integer submissionId,
            @RequestParam(value = "updateById", required = false, defaultValue = "false") boolean updateById,
            @RequestBody Map<String, Object> request) {
        if (updateById) {
            return ResponseEntity.ok("Updated homework submission with custom logic and ID");
        } else {
            return ResponseEntity.ok("Updated homework submission with custom logic");
        }
    }

    @PutMapping("/{id}/homework-submissions/{submissionId}/custom-without-id")
    public ResponseEntity<String> updateHomeworkSubmissionCustomWithoutId(
            @PathVariable Integer id,
            @PathVariable Integer submissionId,
            @RequestBody Map<String, Object> request) {
        return ResponseEntity.ok("Updated homework submission with custom logic without ID");
    }


}

