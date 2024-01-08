package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.Course;
import org.example.model.Professors;
import org.example.model.Students;
import org.example.model.*;
import org.example.repository.CourseMaterialRepository;
import org.example.repository.CourseRepository;
import org.example.repository.StudentCourseRepository;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Integer Id) {
        return courseRepository.findById(Id);
    }

    public Course addCourses(Course course) {
        course.setCourseID(null);
        return courseRepository.save(course);
    }

    public boolean deleteCourse(Integer Id) {
        Optional<Course> existingCourse = courseRepository.findById(Id);
        if (existingCourse.isPresent()) {
            courseRepository.deleteById(Id);
            return true;
        } else throw new EntityNotFoundException("Course with id " + Id + " has not been found");
    }

    public Professors getProfessorFromCourse(Course course){
        return courseRepository.findProfessorByCourseId(course.getCourseID());
    }
    public CourseMaterialResponse addCourseMaterial(Integer courseId, String content, String title) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " not found"));
        CourseMaterial courseMaterial = new CourseMaterial();
        courseMaterial.setTitle(title);
        courseMaterial.setContent(content);
        courseMaterial.setCourse(course);
        if (course.getCourseMaterials() == null) {
            course.setCourseMaterials(new ArrayList<>());
        }
        course.getCourseMaterials().add(courseMaterial);
        CourseMaterial savedCourseMaterial = courseMaterialRepository.save(courseMaterial);
        course.getCourseMaterials().add(savedCourseMaterial);
        CourseMaterialResponse response = new CourseMaterialResponse();
        response.setMaterialId(savedCourseMaterial.getMaterialId());
        return response;
    }

    public boolean deleteCourseMaterial(Integer courseId, Long materialId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.getCourseMaterials().removeIf(submission -> submission.getMaterialId().equals(materialId));
            courseRepository.save(course);
            return true;
        }
        return false;
    }

    public List<String> getStudentEmailsByCourse(Integer courseId) {
        List<StudentCourse> studentCourses = studentCourseRepository.findAll().stream().
                filter((StudentCourse a) -> Objects.equals(courseId, a.getCourse().getCourseID())).toList();

        List<String> studMails = new ArrayList<>();
        for (StudentCourse x : studentCourses) {
            studMails.add(x.getStudent().getEmail());
        }
        return studMails;
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
            String isOptional = (String) row[1];
            String firstname = (String) row[2];
            String lastname = (String) row[3];
            response = response + "<tr>"+
                    "<td><input type='checkbox' name='ids' value='" + courseName + "'></td>" +
                    "<td>" + courseName + "</td>" +
                    "<td>" + isOptional + "</td>" +
                    "<td>" + firstname + " " + lastname + "</td>" +
                    "</tr>";
        }
        return response;
    }

    public String getCurrentEnrolledCourses(String email){

        List<Object[]> courseResults = this.courseRepository.findEnrolledCoursesOfStudent(email);
        String response = "";

        for(Object[] row: courseResults){
            String courseName = (String) row[0];
            String isOptional = (String) row[1];
            String firstname = (String) row[2];
            String lastname = (String) row[3];
            response = response + "<tr>"+
                    "<td><input type='checkbox' name='ids' value='" + courseName + "'></td>" +
                    "<td>" + courseName + "</td>" +
                    "<td>" + isOptional + "</td>" +
                    "<td>" + firstname + " " + lastname + "</td>" +
                    "</tr>";
        }
        return response;
    }

    public boolean addListOfCourses(List<String> ids, String email){
        Students student = this.studentRepository.findByEmail(email);
        for (String id : ids) {
            Course course = this.courseRepository.findByName(id);
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setCourse(course);
            studentCourse.setStudent(student);
            studentCourse.setAttendance(Attendance.ABS);
            studentCourse.setNote(0);

            student.getStudentCourses().add(studentCourse);
            course.getStudentCourses().add(studentCourse);

            this.courseRepository.saveAndFlush(course);
            this.studentRepository.saveAndFlush(student);
            this.studentCourseRepository.save(studentCourse);
        }
        return true;
    }
}


