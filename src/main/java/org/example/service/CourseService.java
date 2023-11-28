package org.example.service;

import org.example.model.Course;
import org.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository){
        this.courseRepository=courseRepository;
    }

    public Course saveCourse(Course course){
        if(validateCourse(course)){
            return this.courseRepository.save(course);
        }
        return null;
    }

    public boolean validateCourse(Course course){
        if (course.getName() == null || course.getAbbreviation() == null ||
                course.getProf() == null || course.getYearOfStudy() > 3 ||
                course.getYearOfStudy() < 1 || course.getType() == null ||
                course.getAbbreviation().length()>course.getName().length())
            return false;
        List<Course> courseList=this.courseRepository.findAll();
        for(Course c:courseList){
            if(Objects.equals(c,course) || Objects.equals(c.getName(),course.getName()) ||
                    Objects.equals(c.getAbbreviation(),course.getAbbreviation()))
                return false;
        }
        return true;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseById(int cId){
        return courseRepository.findById(cId).orElse(null);
    }
    public Course updateCourse(Course course){
        if(courseRepository.existsById(course.getId())){
            return courseRepository.save(course);
        }
        return null;
    }

    public void deleteCourse(int cId){
        courseRepository.deleteById(cId);
    }
}
