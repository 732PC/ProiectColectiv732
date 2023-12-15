package org.example.service;

import org.example.model.Course;
import org.example.model.Student;
import org.example.model.User;
import org.example.repository.CourseRepository;
import org.example.repository.StudentRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository, StudentRepository studentRepository){
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    public String getAllOptionalCourses(){

        List<Object[]> courseResults = this.courseRepository.findOptionalCourses();
        String response = "<tr>\n" +
                "                    <th>Select</th>\n" +
                "                    <th>Course Name</th>\n" +
                "                    <th>Optional</th>\n" +
                "                    <th>Teacher</th>\n" +
                "                    <th>Day of Week</th>\n" +
                "                    <th>Time</th>\n" +
                "                </tr>";

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

    public boolean addListOfCourses(List<String> ids, String email){
        User user = this.userRepository.findByEmail(email);
        Student student = this.studentRepository.findByUser(user);
        for(String id:ids){
            Course course = this.courseRepository.findByCourseName(id);
            List<Student> students = course.getStudents();
            students.add(student);
            course.setStudents(students);
            List<Course> courses = student.getCourses();
            courses.add(course);
            student.setCourses(courses);
            this.studentRepository.save(student);
            this.courseRepository.save(course);
        }
        return true;
    }
}
