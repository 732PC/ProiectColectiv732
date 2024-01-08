package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StudentCourseRequestDTO {
    private Integer studentId;
    private Integer courseId;
    private boolean attendance;
    private Integer grade;
}
