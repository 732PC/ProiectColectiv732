package org.example.controller;

import org.example.model.Course;
import org.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepo;

    @Autowired
    public CourseController(CourseRepository courseRepo){
        this.courseRepo=courseRepo;
    }

    public Course addCourse(Course course){
        if(validateCourse(course)){
            return this.courseRepo.save(course);
        }
        return null;
    }

    private boolean validateName(String name){
        if(name==null)
            return false;
        List<Course> courseList=this.courseRepo.findAll();
        for(Course course:courseList){
            if(Objects.equals(course.getName(),name))
                return false;
        }
        Pattern pattern = Pattern.compile("^[A-Z][a-z]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
    public boolean validateCourse(Course course){
        if (course.getName() == null || course.getAbbreviation() == null ||
                course.getProf() == null || course.getYearOfStudy() > 3 ||
                course.getYearOfStudy() < 1 || course.getType() == null ||
                course.getAbbreviation().length()>course.getName().length())
            return false;
        List<Course> courseList=this.courseRepo.findAll();
        for(Course c:courseList){
            if(Objects.equals(c,course) || Objects.equals(c.getName(),course.getName()) ||
            Objects.equals(c.getAbbreviation(),course.getAbbreviation()))
                return false;
        }
        return true;
    }
}
