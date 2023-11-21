package org.example.controller;

import org.example.exception.BusinessException;
import org.example.exception.BusinessExceptionCode;
import org.example.model.Course;
import org.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService=courseService;
    }

    @PostMapping("/register")
    public ResponseEntity<Course> saveCourse(@RequestBody Course course) throws BusinessException{
        Course savedCourse = this.courseService.saveCourse(course);

        if(savedCourse==null)
            throw new BusinessException(BusinessExceptionCode.INVALID_COURSE);
        else
            return new ResponseEntity<>(savedCourse, HttpStatus.OK);
    }
}
