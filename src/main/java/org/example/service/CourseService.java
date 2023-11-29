package org.example.service;

import jakarta.persistence.*;
import org.example.model.Course;
import org.example.model.CustomCourseResult;
import org.example.model.FrontendFacingCourse;
import org.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
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
            response = response + "<tr><th>" + courseName + "</th><th>" + isOptional + "</th><th>" + firstname + " " +
                    lastname + "</th><th>" + dayOfWeek + "</th><th>" + time + "</th></tr>";
        }
        return response;
    }
}
