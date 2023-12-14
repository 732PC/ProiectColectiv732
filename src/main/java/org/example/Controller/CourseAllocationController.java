package org.example.Controller;

import org.example.Model.Course;
import org.example.Model.Students;
import org.example.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/course-allocation")
public class CourseAllocationController {


    private EnrollmentService enrollmentService;
    @Autowired
    public CourseAllocationController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    @PostMapping("/assign-courses-for-study-year")
    public ResponseEntity<String> assignCoursesToStudentForStudyYear(

            @RequestParam("studyYear") int studyYear) {

        enrollmentService.assignRequiredCoursesToStudentForStudyYear(studyYear);

        return ResponseEntity.ok("Courses assigned successfully for the specified study year");
    }

    @PostMapping("/assign-courses-automatically/{studyYear}")
    public ResponseEntity<String> assignCoursesAutomatically(@PathVariable("studyYear") int studyYear) {
        try {
            enrollmentService.assignRequiredCoursesToStudentAutomatically(studyYear);
            return ResponseEntity.ok("Courses assigned automatically for the specified study year");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning courses: " + e.getMessage());
        }
    }
    @GetMapping("/all")
    public List<Students> getStudents(){
        return enrollmentService.getStudents();
    }

    @GetMapping("/allCourses")
    public List<Course> getCourses(){
        return enrollmentService.getCourses();
    }

    @GetMapping("/getStudyYears")
    public Set<Integer> getAllStudyYears(){
        return enrollmentService.getAllStudyYears();
    }

}
