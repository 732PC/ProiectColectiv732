package org.example.service;

import org.example.model.Course;
import org.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course saveCourse(Course course) {
        if (validateCourse(course)) {
            return this.courseRepository.save(course);
        }
        return null;
    }

    public boolean validateCourse(Course course) {
//        if (course.getName() == null || course.getAbbreviation() == null ||
//                course.getProf() == null || course.getYearOfStudy() > 3 ||
//                course.getYearOfStudy() < 1 || course.getType() == null ||
//                course.getAbbreviation().length()>course.getName().length())
//            return false;
//        List<Course> courseList=this.courseRepository.findAll();
//        for(Course c:courseList){
//            if(Objects.equals(c,course) || Objects.equals(c.getName(),course.getName()) ||
//                    Objects.equals(c.getAbbreviation(),course.getAbbreviation()))
//                return false;
//        }
        return true;
    }

    public String getAllCourses() {


        List<Course> courseResults = this.courseRepository.findAll();
        String response = "";
        for (Course row : courseResults) {
            response+="<div class='profesor-box'> <p>Name :"+row.getName()+"</p>"+
            "<p>ABR: "+row.getAbbreviation()+"</p>"+
            "<p>Credit Number:"+ row.getCreditNr()+"</p>"+
            "<p>Year of Study: "+row.getYearOfStudy()+"</p>"+
            "<p>Type:"+row.getType()+"</p>"+
            "<button class='buttonModify' onclick='editCourse("+row.getId()+")'>Modify Course</button></div>";
        }return response;

    }

    public Course getCourseById(int cId) {
        return courseRepository.findById(cId).orElse(null);
    }

    public Course updateCourse(Course course) {
        if (courseRepository.existsById(course.getId())) {
            return courseRepository.save(course);
        }
        return null;
    }

    public void deleteCourse(int cId) {
        courseRepository.deleteById(cId);
    }
}
