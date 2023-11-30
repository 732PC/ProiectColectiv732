package org.example.controller;

import org.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
