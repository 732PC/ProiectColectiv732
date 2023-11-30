package org.example.service;

import org.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public String getAllOptionalCourses(){

        List<Object[]> courseResults = this.courseRepository.findOptionalCourses();
        String response = "";

        for(Object[] row: courseResults){
            String courseName = (String) row[0];
            boolean isOptional = (boolean) row[1];
            String firstname = (String) row[2];
            String lastname = (String) row[3];
            String dayOfWeek = (String) row[4];
            Time time = (Time) row[5];
            response = response + "<tr>"+
                    "<td><input type='checkbox' name='ids' value='" + courseName + "'></td>" +
                    "<td>" + courseName + "</td>" +
                    "<td>" + isOptional + "</td>" +
                    "<td>" + firstname + " " + lastname + "</td>" +
                    "<td>" + dayOfWeek + "</td>" +
                    "<td>" + time + "</td>" +
                    "</tr>";
        }
        return response;
    }

    public String getCurrentEnrolledCourses(String email){

        List<Object[]> courseResults = this.courseRepository.findEnrolledCoursesOfStudent(email);
        String response = "";

        for(Object[] row: courseResults){
            String courseName = (String) row[0];
            boolean isOptional = (boolean) row[1];
            String firstname = (String) row[2];
            String lastname = (String) row[3];
            String dayOfWeek = (String) row[4];
            Time time = (Time) row[5];
            response = response + "<tr>"+
                    "<td><input type='checkbox' name='ids' value='" + courseName + "'></td>" +
                    "<td>" + courseName + "</td>" +
                    "<td>" + isOptional + "</td>" +
                    "<td>" + firstname + " " + lastname + "</td>" +
                    "<td>" + dayOfWeek + "</td>" +
                    "<td>" + time + "</td>" +
                    "</tr>";
        }
        return response;
    }
}
