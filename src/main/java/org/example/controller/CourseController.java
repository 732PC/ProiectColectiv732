package org.example.controller;

import org.example.exception.BusinessException;
import org.example.exception.BusinessExceptionCode;
import org.example.model.Course;
import org.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses(){
        List<Course> courses=courseService.getAllCourses();
        return new ResponseEntity<>(courses,HttpStatus.OK);
    }

    @PostMapping("/courses")
    public String listAllCourses(Model model){
        List<Course> courses=courseService.getAllCourses();
        model.addAttribute("courses",courses);
        return "courses";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id){
        Course course=courseService.getCourseById(id);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(course);
        if (updatedCourse != null) {
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
