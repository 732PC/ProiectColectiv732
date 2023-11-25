package org.example.Controller;

import org.example.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course-allocation")
public class CourseAllocationController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/assign-courses-for-study-year")
    public ResponseEntity<String> assignCoursesToStudentForStudyYear(
            @RequestParam("studentId") int studentId,
            @RequestParam("studyYear") int studyYear) {

        enrollmentService.assignRequiredCoursesToStudentForStudyYear(studentId, studyYear);

        return ResponseEntity.ok("Courses assigned successfully for the specified study year");
    }

    @PostMapping("/assign-courses-automatically")
    public ResponseEntity<String> assignCoursesToStudentAutomatically(@RequestParam("studentId") int studentId) {
        enrollmentService.assignRequiredCoursesToStudentAutomatically(studentId);

        return ResponseEntity.ok("Courses assigned automatically based on current study year");
    }
}
