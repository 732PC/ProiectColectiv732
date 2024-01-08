package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.Course;
import org.example.service.CourseService;
import org.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
            Optional<Course> courseOptional = courseService.getCourseById(id);
            if (courseOptional.isPresent()) {
                courseService.addCourseMaterial(id, content, title);
                Course course = courseOptional.get();
                String courseName = course.getName();
                String emailContent = emailService.configureEmailTemplateCourseMaterials(courseName, title);
                List<String> studMails = courseService.getStudentEmailsByCourse(id);
                for (String mail : studMails) {
                    emailService.sendEmailFromTemplate(mail, "New Course Material Loaded", emailContent);
                }
                return ResponseEntity.ok("Course material added successfully");
            } else throw (new EntityNotFoundException());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (FileNotFoundException e) {
            return ResponseEntity.ok("Course material added successfully but email was not sent");
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

    @GetMapping(value = "/course")
    public ResponseEntity<String> getRouter(@RequestParam String type, @RequestParam String email){
        if(Objects.equals(type, "allOptionals")) {
            String courses = this.courseService.getAllOptionalCourses();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } else if(Objects.equals(type, "currentEnrolledCourses")) {
            String courses = this.courseService.getCurrentEnrolledCourses(email);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping(value = "/course")
    public ResponseEntity<String> addCourses(@RequestParam List<String> ids, @RequestParam String email){
        if(ids.size() == 0 || email.length() == 0){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        boolean successful = this.courseService.addListOfCourses(ids, email);
        if(successful){
            return new ResponseEntity<>("All good!", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
        }
    }
}
