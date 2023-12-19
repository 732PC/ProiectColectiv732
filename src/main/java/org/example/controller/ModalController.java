package org.example.controller;

import org.example.model.Course;
import org.example.model.StudentCourse;
import org.example.service.CourseService;
import org.example.service.ModalService;
import org.example.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class ModalController {

    private final ModalService modalService;
    private final CourseService courseService;
    private final StudentCourseService studentCourseService;

    @Autowired
    public ModalController(ModalService modalService, CourseService courseService,
                           StudentCourseService studentCourseService) {
        this.modalService = modalService;
        this.courseService = courseService;
        this.studentCourseService = studentCourseService;
    }

    @GetMapping("/attendances-modal-content")
    public String getAttendancesModalContent(@RequestParam("courseId") Integer courseId) throws IOException {
        Course course = courseService.getCourseById(courseId).orElseThrow();

        String courseTitle = course.getName();
        String professorName = courseService.getProfessorFromCourse(course).getLastname();

        List<StudentCourse> students = studentCourseService.getAllById(courseId);

        return modalService.generateModalContent(courseTitle, professorName, students);
    }
}
