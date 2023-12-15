package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
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
        boolean successful = this.courseService.addListOfCourses(ids, email);
        if(successful){
            return new ResponseEntity<>("All good!", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
        }
    }

}
