package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.Course;
import org.example.service.CourseService;
import org.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id) {
        Course course = courseService.getCourseById(id).orElse(null);
        return (course != null) ? ResponseEntity.ok(course) : ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        {
            Course addedCourse = courseService.addCourses(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedCourse);

        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping("/{id}/course-materials")
    public ResponseEntity<String> addCourseMaterial(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody) {
        String content = requestBody.get("content");
        String title = requestBody.get("title");
        try {

            courseService.addCourseMaterial(id, content, title);
            String courseName = courseService.getCourseById(id).get().getName();
            String emailContent = emailService.configureEmailTemplateCourseMaterials(courseName);
            List<String> studMails = courseService.getStudentEmailsByCourse(id);
            for (String mail : studMails) {
                emailService.sendEmailFromTemplate(mail, "New Course Material Loaded", emailContent);
            }

            return ResponseEntity.ok("Course material added successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/course-materials/{materialId}")
    public ResponseEntity<?> deleteCourseMaterial(
            @PathVariable Integer id,
            @PathVariable Long materialId) {

        try {
            boolean deleted = courseService.deleteCourseMaterial(id, materialId);

            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
